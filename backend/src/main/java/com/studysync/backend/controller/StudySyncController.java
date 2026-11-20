package com.studysync.backend.controller;

import com.studysync.backend.dto.LeaderboardResponse;
import com.studysync.backend.entity.*;
import com.studysync.backend.repository.*;
import com.studysync.backend.service.StudySyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class StudySyncController {

    @Autowired
    private StudySyncService service;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudyRoomRepository roomRepository;

    @Autowired
    private DoubtTicketRepository doubtRepository;

    // Students
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) {
        if (student.getKarmaPoints() == null) {
            student.setKarmaPoints(0);
        }
        return studentRepository.save(student);
    }

    // Study Rooms
    @GetMapping("/rooms")
    public List<StudyRoom> getAllRooms() {
        return roomRepository.findAll();
    }

    @PostMapping("/rooms")
    public StudyRoom createRoom(@RequestBody StudyRoom room) {
        if (room.getStatus() == null) {
            room.setStatus(StudyRoom.RoomStatus.UPCOMING);
        }
        if (room.getMaxCapacity() == null) {
            room.setMaxCapacity(5);
        }
        return roomRepository.save(room);
    }

    @PostMapping("/rooms/{roomId}/join")
    public StudyRoom joinRoom(@PathVariable Long roomId, @RequestParam Long studentId) {
        return service.joinRoom(roomId, studentId);
    }

    @PostMapping("/rooms/{roomId}/complete")
    public StudyRoom completeRoom(@PathVariable Long roomId) {
        return service.completeRoom(roomId);
    }

    // Doubt Tickets
    @GetMapping("/doubts")
    public List<DoubtTicket> getDoubts(@RequestParam(required = false) Long studentId) {
        return service.getDoubtsForStudent(studentId);
    }

    @PostMapping("/doubts")
    public DoubtTicket createDoubt(@RequestBody DoubtTicket doubt) {
        if (doubt.getStatus() == null) {
            doubt.setStatus(DoubtTicket.TicketStatus.OPEN);
        }
        return doubtRepository.save(doubt);
    }

    @PostMapping("/doubts/{id}/accept")
    public DoubtTicket acceptDoubt(@PathVariable Long id, @RequestParam Long mentorId) {
        return service.acceptDoubt(id, mentorId);
    }

    @PostMapping("/doubts/{id}/resolve")
    public DoubtTicket resolveDoubt(@PathVariable Long id) {
        return service.resolveDoubt(id);
    }

    // Analytics / Leaderboard
    @GetMapping("/analytics/leaderboard")
    public LeaderboardResponse getLeaderboard() {
        return service.getLeaderboardAndStats();
    }
}
