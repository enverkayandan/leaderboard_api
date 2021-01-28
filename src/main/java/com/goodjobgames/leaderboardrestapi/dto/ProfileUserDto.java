package com.goodjobgames.leaderboardrestapi.dto;

import java.util.UUID;

public class ProfileUserDto {
    Integer rank;
    Double points;
    UUID userId;
    String displayName;

    public void setPoints(Double points) {
        this.points = points;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
