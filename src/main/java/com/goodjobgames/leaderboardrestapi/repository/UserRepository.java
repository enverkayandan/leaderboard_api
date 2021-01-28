package com.goodjobgames.leaderboardrestapi.repository;

import com.goodjobgames.leaderboardrestapi.entity.RankingUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<RankingUser, UUID> {

    RankingUser findFirstByUpIsNull();

    RankingUser findFirstByDownIsNull();

    Boolean existsByDisplayName(String displayName);

//    @Query("select r from RankingUser as r where :countryIso is null or r.countryIso = :countryIso")
//    List<RankingUser> getLeaderboard(String countryIso);
}
