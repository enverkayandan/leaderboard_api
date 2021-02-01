package com.goodjobgames.leaderboardrestapi.service;

import com.goodjobgames.leaderboardrestapi.dto.LeaderboardUserDto;
import com.goodjobgames.leaderboardrestapi.dto.ProfileUserDto;
import com.goodjobgames.leaderboardrestapi.dto.SubmitScoreDto;
import com.goodjobgames.leaderboardrestapi.entity.RankingUser;
import com.goodjobgames.leaderboardrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    private RankingUser fetchUp(UUID userId) {
        EntityGraph entityGraph = entityManager.getEntityGraph("up-user-graph");
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        return entityManager.find(RankingUser.class, userId, properties);
    }

    private RankingUser fetchDown(UUID userId) {
        EntityGraph entityGraph = entityManager.getEntityGraph("down-user-graph");
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        return entityManager.find(RankingUser.class, userId, properties);
    }


    @Override
    public RankingUser findUserById(UUID id) {
        Optional<RankingUser> optional = userRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public RankingUser getTopUser() {
        return userRepository.findFirstByUpIsNull();
    }

    @Override
    public RankingUser createUser(String displayName, String countryIso) {
        RankingUser newUser = new RankingUser();
        RankingUser lastUser = userRepository.findFirstByDownIsNull();

        newUser.setDisplayName(displayName);
        newUser.setCountryIso(countryIso.toLowerCase());
        newUser.setPoints(0.0);

        userRepository.save(newUser);

        if(lastUser != null) {
            lastUser.setDown(newUser);
            newUser.setUp(lastUser);
            userRepository.save(newUser);
            userRepository.saveAndFlush(lastUser);
        } else {
            userRepository.save(newUser);
        }

        return newUser;
    }

    @Override
    public List<LeaderboardUserDto> getLeaderboard(String countryIso) {
        List<LeaderboardUserDto> result = new ArrayList<>();
        RankingUser temp = fetchDown(getTopUser().getId());
        Integer rank = 1;
        while (temp != null) {
            if(countryIso.equals("") || countryIso.equals(temp.getCountryIso())) {
                result.add(temp.toLeaderboardDto(rank));
                rank++;
            }
            temp = temp.getDown();
        }

        return result;
    }

    @Override
    public ProfileUserDto submitScore(SubmitScoreDto submitScoreDto) {
        RankingUser user = findUserById(submitScoreDto.getUserId());
        if (user != null) {
            //Eager fetching leaderboard
            user = fetchUp(user.getId());
            if(user.getUp() != null) {
                RankingUser newUp = user.getUp();
                Double newTotalScore = user.getPoints() + submitScoreDto.getNewScore();
                //Searching for users new place
                while (newUp != null && newTotalScore >= newUp.getPoints()) {
                    newUp = newUp.getUp();
                }
                //Checking if placement has changed
                if(!user.getUp().equals(newUp)){
                    user.getUp().setDown(user.getDown());
                    userRepository.save(user.getUp());
                    if (user.getDown() != null) {
                        user.getDown().setUp(user.getUp());
                        userRepository.save(user.getDown());
                    }
                    user.setUp(newUp);
                    if (newUp != null) {
                        user.setDown(newUp.getDown());
                        user.getDown().setUp(user);
                        newUp.setDown(user);
                        userRepository.save(newUp);
                    } else {
                        RankingUser topUser = getTopUser();
                        user.setDown(topUser);
                        topUser.setUp(user);
                        userRepository.save(topUser);
                    }
                }
            }
            user.setPoints(user.getPoints() + submitScoreDto.getNewScore());
            userRepository.save(user);
        }

        return user.toProfileDto();
    }

    @Override
    public List<RankingUser> findAll() {
        return userRepository.findAll();
    }
}
