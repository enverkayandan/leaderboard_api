package com.goodjobgames.leaderboardrestapi.entity;

import com.goodjobgames.leaderboardrestapi.dto.LeaderboardUserDto;
import com.goodjobgames.leaderboardrestapi.dto.ProfileUserDto;

import javax.persistence.*;
import java.util.UUID;

@NamedEntityGraph(
        name = "down-user-graph",
        attributeNodes = {
                @NamedAttributeNode("down")
        }
)

@NamedEntityGraph(
        name = "up-user-graph",
        attributeNodes = {
                @NamedAttributeNode("up")
        }
)

@Entity
@Table(name = "ranking_users")
public class RankingUser {
    private UUID id;
    private Double points;
    private String displayName, countryIso;
    private RankingUser up, down;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Column(name = "points", columnDefinition = "double")
    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    @Column(name = "display_name", nullable = false, columnDefinition = "TEXT")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(name = "country_iso", nullable = false, columnDefinition = "TEXT")
    public String getCountryIso() {
        return countryIso;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "up_user", referencedColumnName = "id", columnDefinition = "uuid")
    public RankingUser getUp() {
        return up;
    }

    public void setUp(RankingUser up) {
        this.up = up;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "down_user", referencedColumnName = "id", columnDefinition = "uuid")
    public RankingUser getDown() {
        return down;
    }

    public void setDown(RankingUser down) {
        this.down = down;
    }

    public Integer calculateRanking() {
        Integer result = 1;
        RankingUser user = this;
        while (user.getUp() != null) {
            user = user.getUp();
            result++;
        }

        return result;
    }

    public ProfileUserDto toProfileDto() {
        ProfileUserDto dto = new ProfileUserDto();
        dto.setUserId(getId());
        dto.setDisplayName(getDisplayName());
        dto.setPoints(getPoints());
        dto.setRank(calculateRanking());
        return dto;
    }

    public LeaderboardUserDto toLeaderboardDto(Integer rank) {
        LeaderboardUserDto dto = new LeaderboardUserDto();
        dto.setDisplayName(getDisplayName());
        dto.setPoints(getPoints());
        dto.setRank(rank);
        dto.setCountryIso(getCountryIso());
        return dto;
    }
}