package com.goodjobgames.leaderboardrestapi.service;

import com.goodjobgames.leaderboardrestapi.entity.RankingUser;
import com.goodjobgames.leaderboardrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RankingUser findUserById(UUID id) {
        Optional<RankingUser> optional = userRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public RankingUser findTopUser() {
        return userRepository.findFirstByUpIsNull();
    }

    @Override
    public RankingUser findLastUser() {
        return userRepository.findFirstByDownIsNull();
    }

    @Override
    public RankingUser createUser(String displayName, String countryIso) {
        RankingUser newUser = new RankingUser();
        RankingUser lastUser = userRepository.findFirstByDownIsNull();

        newUser.setDisplayName(displayName);
        newUser.setCountryIso(countryIso.toLowerCase());
        newUser.setPoints(0.0);

        userRepository.save(newUser);

        lastUser.setDown(newUser);
        newUser.setUp(lastUser);

        return newUser;
    }
}
