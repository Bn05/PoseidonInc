package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.User;
import com.nnk.poseidoninc.Service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerAPI {

    private UserService userService;

    public UserControllerAPI(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public Iterable<User> getUsers() {

        return userService.findAll();
    }
}
