package com.goodjobgames.leaderboardrestapi.rest;

import com.goodjobgames.leaderboardrestapi.dto.LeaderboardUserDto;
import com.goodjobgames.leaderboardrestapi.dto.ProfileUserDto;
import com.goodjobgames.leaderboardrestapi.dto.SubmitScoreDto;
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

    @GetMapping("/leaderboard")
    public List<LeaderboardUserDto> getLeaderboard() {
        return userService.getLeaderboard("");
        }

    @GetMapping("/leaderboard/{countryIso}")
    public List<LeaderboardUserDto> getLeaderboard(@PathVariable String countryIso) {
        return userService.getLeaderboard(countryIso);
    }

    @PostMapping("/score/submit")
    public ProfileUserDto submitScore(@RequestBody SubmitScoreDto submitScoreDto) {
        return userService.submitScore(submitScoreDto);
    }
}
