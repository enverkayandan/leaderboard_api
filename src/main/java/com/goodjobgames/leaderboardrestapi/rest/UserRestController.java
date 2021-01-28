package com.goodjobgames.leaderboardrestapi.rest;

import com.goodjobgames.leaderboardrestapi.dto.CreateUserDto;
import com.goodjobgames.leaderboardrestapi.dto.ProfileUserDto;
import com.goodjobgames.leaderboardrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/{id}")
    public ProfileUserDto findUserProfileById(@PathVariable UUID id) {
        return userService.findUserById(id).toProfileDto();
    }

    @PostMapping("/create")
    public ProfileUserDto createUser(@RequestBody CreateUserDto createUserDto) {
        return userService.createUser(createUserDto.getDisplayName(), createUserDto.getCountryIso()).toProfileDto();
    }
}
