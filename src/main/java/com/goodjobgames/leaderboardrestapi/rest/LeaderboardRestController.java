package com.goodjobgames.leaderboardrestapi.rest;

import com.goodjobgames.leaderboardrestapi.dto.LeaderboardUserDto;
import com.goodjobgames.leaderboardrestapi.dto.ProfileUserDto;
import com.goodjobgames.leaderboardrestapi.dto.SubmitScoreDto;
import com.goodjobgames.leaderboardrestapi.entity.RankingUser;
import com.goodjobgames.leaderboardrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("")
public class LeaderboardRestController {
    private UserService userService;

    @Autowired
    public LeaderboardRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/addUsers/{userCount}")
    public void addUsers(@PathVariable int userCount) {
        ArrayList<UUID> ids = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            ids.add(userService.createUser(Integer.toString(i), (Math.random() < 0.4) ? "fr" : "tr").getId());
        }
    }

    @GetMapping("/simulate/{playFactor}")
    public void simulateLeaderboard(@PathVariable int playFactor) {
        List<RankingUser> users = userService.findAll();
        for (RankingUser user : users) {
            long start = System.currentTimeMillis();
            for (int a = 0; a < playFactor; a++) {
                SubmitScoreDto dto = new SubmitScoreDto();
                dto.setUserId(user.getId());
                dto.setNewScore(Math.random()*1);
                submitScore(dto);
            }
            //System.out.println("Simulated user " + user.getDisplayName() + ", " + playCount + " score submissions took " + (System.currentTimeMillis() - start) + " millis(" + ((System.currentTimeMillis() - start)/((playCount != 0) ? playCount : 1)) + " avg.)");
        }
    }


    @GetMapping("/leaderboard")
    public List<LeaderboardUserDto> getLeaderboard() {
        List<LeaderboardUserDto> result = new ArrayList<>();
        RankingUser temp = userService.fetchDown(userService.getTopUser().getId());
        Integer rank = 1;
        while (temp != null) {
            result.add(temp.toLeaderboardDto(rank));
            rank++;
            temp = temp.getDown();
        }

        return result;
    }

    @GetMapping("/leaderboard/{countryIso}")
    public List<LeaderboardUserDto> getLeaderboard(@PathVariable String countryIso) {
        List<LeaderboardUserDto> result = new ArrayList<>();
        RankingUser temp = userService.fetchDown(userService.getTopUser().getId());
        Integer rank = 1;
        while (temp != null) {
            if (temp.getCountryIso().equals(countryIso)) {
                result.add(temp.toLeaderboardDto(rank));
                rank++;
            }
            temp = temp.getDown();
        }

        return result;
    }

    @PostMapping("/score/submit")
    public ProfileUserDto submitScore(@RequestBody SubmitScoreDto submitScoreDto) {
        return userService.submitScore(submitScoreDto);
    }
}
