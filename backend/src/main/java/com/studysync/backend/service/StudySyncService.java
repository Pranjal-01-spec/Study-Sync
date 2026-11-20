package com.studysync.backend.service;

import com.studysync.backend.dto.LeaderboardResponse;
import com.studysync.backend.entity.*;
import com.studysync.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudySyncService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private DoubtTicketRepository doubtTicketRepository;

    public StudyRoom joinRoom(Long roomId, Long studentId) {
        StudyRoom room = studyRoomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        if (!studentRepository.existsById(studentId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }

        if (room.getStatus() == StudyRoom.RoomStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room is already completed");
        }

        if (room.getParticipantIds().contains(studentId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already a participant in this room");
        }

        if (room.getParticipantIds().size() >= room.getMaxCapacity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room capacity reached");
        }

        // Schedule conflict check: start1 < end2 AND start2 < end1
        List<StudyRoom> conflictingRooms = studyRoomRepository.findAll().stream()
                .filter(r -> r.getStatus() == StudyRoom.RoomStatus.UPCOMING)
                .filter(r -> r.getParticipantIds().contains(studentId) || r.getHostId().equals(studentId))
                .filter(r -> room.getStartTime().isBefore(r.getEndTime()) && r.getStartTime().isBefore(room.getEndTime()))
                .toList();

        if (!conflictingRooms.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Schedule conflict: You already have a session booked during this time");
        }

        room.getParticipantIds().add(studentId);
        return studyRoomRepository.save(room);
    }

    public StudyRoom completeRoom(Long roomId) {
        StudyRoom room = studyRoomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        if (room.getStatus() == StudyRoom.RoomStatus.COMPLETED) {
            return room;
        }

        room.setStatus(StudyRoom.RoomStatus.COMPLETED);

        Student host = studentRepository.findById(room.getHostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Host student not found"));

        int currentKarma = host.getKarmaPoints() != null ? host.getKarmaPoints() : 0;
        host.setKarmaPoints(currentKarma + 20);
        studentRepository.save(host);

        return studyRoomRepository.save(room);
    }

    public List<DoubtTicket> getDoubtsForStudent(Long studentId) {
        List<DoubtTicket> allDoubts = doubtTicketRepository.findAll();
        if (studentId == null) {
            return allDoubts;
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        List<String> strongSubjects = student.getStrongSubjects() != null
                ? Arrays.stream(student.getStrongSubjects().split(","))
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .collect(Collectors.toList())
                : List.of();

        for (DoubtTicket doubt : allDoubts) {
            if (doubt.getSubjectTag() != null && strongSubjects.contains(doubt.getSubjectTag().trim().toLowerCase())) {
                doubt.setRecommended(true);
            } else {
                doubt.setRecommended(false);
            }
        }

        return allDoubts;
    }

    public DoubtTicket acceptDoubt(Long doubtId, Long mentorId) {
        DoubtTicket doubt = doubtTicketRepository.findById(doubtId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doubt not found"));

        if (!studentRepository.existsById(mentorId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mentor student not found");
        }

        doubt.setMentorId(mentorId);
        doubt.setStatus(DoubtTicket.TicketStatus.IN_PROGRESS);
        return doubtTicketRepository.save(doubt);
    }

    public DoubtTicket resolveDoubt(Long doubtId) {
        DoubtTicket doubt = doubtTicketRepository.findById(doubtId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doubt not found"));

        doubt.setStatus(DoubtTicket.TicketStatus.RESOLVED);

        if (doubt.getMentorId() != null) {
            Student mentor = studentRepository.findById(doubt.getMentorId()).orElse(null);
            if (mentor != null) {
                int currentKarma = mentor.getKarmaPoints() != null ? mentor.getKarmaPoints() : 0;
                mentor.setKarmaPoints(currentKarma + 15);
                studentRepository.save(mentor);
            }
        }

        return doubtTicketRepository.save(doubt);
    }

    public LeaderboardResponse getLeaderboardAndStats() {
        List<Student> leaderboard = studentRepository.findAll().stream()
                .sorted((s1, s2) -> {
                    int k1 = s1.getKarmaPoints() != null ? s1.getKarmaPoints() : 0;
                    int k2 = s2.getKarmaPoints() != null ? s2.getKarmaPoints() : 0;
                    return Integer.compare(k2, k1);
                })
                .toList();

        long activeRooms = studyRoomRepository.findAll().stream()
                .filter(r -> r.getStatus() == StudyRoom.RoomStatus.UPCOMING)
                .count();

        long openDoubts = doubtTicketRepository.findAll().stream()
                .filter(d -> d.getStatus() == DoubtTicket.TicketStatus.OPEN)
                .count();

        long resolvedDoubts = doubtTicketRepository.findAll().stream()
                .filter(d -> d.getStatus() == DoubtTicket.TicketStatus.RESOLVED)
                .count();

        return new LeaderboardResponse(leaderboard, activeRooms, openDoubts, resolvedDoubts);
    }
}
