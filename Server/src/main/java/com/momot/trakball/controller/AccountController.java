package com.momot.trakball.controller;

import com.momot.trakball.dao.Squad;
import com.momot.trakball.dao.User;
import com.momot.trakball.manager.SquadManager;
import com.momot.trakball.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserManager userManager;

    @PostMapping("/login")
    public User login(@RequestBody User user){
        return userManager.save(user);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userManager.save(user);
    }

}
