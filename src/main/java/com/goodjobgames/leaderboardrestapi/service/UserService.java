package com.goodjobgames.leaderboardrestapi.service;

import com.goodjobgames.leaderboardrestapi.dto.ProfileUserDto;
import com.goodjobgames.leaderboardrestapi.dto.SubmitScoreDto;
import com.goodjobgames.leaderboardrestapi.entity.RankingUser;

import java.util.List;
import java.util.UUID;

public interface UserService {
    RankingUser findUserById(UUID id);

    RankingUser getTopUser();

    RankingUser getLastUser();

    RankingUser fetchUp(UUID userId);

    RankingUser fetchDown(UUID userId);

    RankingUser createUser(String displayName, String countryIso);

    ProfileUserDto submitScore(SubmitScoreDto submitScoreDto);

    List<RankingUser> findAll();
}
