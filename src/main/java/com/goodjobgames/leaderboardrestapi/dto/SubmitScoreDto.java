package com.goodjobgames.leaderboardrestapi.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class SubmitScoreDto {
    Double newScore;
    UUID userId;
    Timestamp timestamp;

    public Double getNewScore() {
        return newScore;
    }

    public void setNewScore(Double newScore) {
        this.newScore = newScore;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
