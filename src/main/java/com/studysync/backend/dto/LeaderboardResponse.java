package com.studysync.backend.dto;

import com.studysync.backend.entity.Student;
import java.util.List;

public class LeaderboardResponse {
    private List<Student> leaderboard;
    private long activeRooms;
    private long openDoubts;
    private long resolvedDoubts;

    public LeaderboardResponse() {}

    public LeaderboardResponse(List<Student> leaderboard, long activeRooms, long openDoubts, long resolvedDoubts) {
        this.leaderboard = leaderboard;
        this.activeRooms = activeRooms;
        this.openDoubts = openDoubts;
        this.resolvedDoubts = resolvedDoubts;
    }

    public List<Student> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<Student> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public long getActiveRooms() {
        return activeRooms;
    }

    public void setActiveRooms(long activeRooms) {
        this.activeRooms = activeRooms;
    }

    public long getOpenDoubts() {
        return openDoubts;
    }

    public void setOpenDoubts(long openDoubts) {
        this.openDoubts = openDoubts;
    }

    public long getResolvedDoubts() {
        return resolvedDoubts;
    }

    public void setResolvedDoubts(long resolvedDoubts) {
        this.resolvedDoubts = resolvedDoubts;
    }
}
