package com.goodjobgames.leaderboardrestapi.service;

import com.goodjobgames.leaderboardrestapi.entity.RankingUser;

import java.util.UUID;

public interface UserService {
    RankingUser findUserById(UUID id);

    RankingUser findTopUser();

    RankingUser findLastUser();

    RankingUser createUser(String displayName, String countryIso);
}
