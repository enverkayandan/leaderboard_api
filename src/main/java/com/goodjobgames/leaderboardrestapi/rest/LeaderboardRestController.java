package com.goodjobgames.leaderboardrestapi.rest;

import com.goodjobgames.leaderboardrestapi.dto.LeaderboardUserDto;
import com.goodjobgames.leaderboardrestapi.dto.ProfileUserDto;
import com.goodjobgames.leaderboardrestapi.dto.SubmitScoreDto;
import com.goodjobgames.leaderboardrestapi.entity.RankingUser;
import com.goodjobgames.leaderboardrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class LeaderboardRestController {
    private UserService userService;
    private RankingUser topUser;

    @Autowired
    public LeaderboardRestController(UserService userService) {
        this.userService = userService;
        topUser = userService.findTopUser();
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardUserDto> getLeaderboard() {
        List<LeaderboardUserDto> result = new ArrayList<>();
        RankingUser temp = topUser;

        while (temp != null) {
            result.add(temp.toLeaderboardDto());
            temp = temp.getDown();
        }

        return result;
    }

    @GetMapping("/leaderboard/{country_iso}")
    public List<LeaderboardUserDto> getLeaderboard(@PathVariable String countryIso) {
        List<LeaderboardUserDto> result = new ArrayList<>();
        RankingUser temp = topUser;

        while (temp != null) {
            if (temp.getCountryIso().equals(countryIso)) {
                result.add(temp.toLeaderboardDto());
            }
            temp = temp.getDown();
        }

        return result;
    }

    @PostMapping("/score/submit")
    public ProfileUserDto submitScore(@RequestBody SubmitScoreDto submitScoreDto) {
        RankingUser user = userService.findUserById(submitScoreDto.getUserId());
        if (user != null) {
            user.getUp().setDown(user.getDown());
            user.getDown().setUp(user.getUp());

            RankingUser newUp = user.getUp();
            Double newTotalScore = user.getPoints() + submitScoreDto.getNewScore();
            while (newUp.getPoints() > newTotalScore) {
                newUp = newUp.getUp();
            }
            if (newUp == null) {
                topUser = user;
            }
            user.setUp(newUp);
            user.setDown(newUp.getDown());
            user.getDown().setUp(user);
            user.getUp().setDown(user);

        }

        return user.toProfileDto();
    }
}
