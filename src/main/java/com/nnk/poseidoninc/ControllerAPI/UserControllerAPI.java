package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.User;
import com.nnk.poseidoninc.Service.Interface.IUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserControllerAPI {

    private IUserService userService;

    public UserControllerAPI(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "userList")
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @PostMapping(value = "/user")
    public UserDto create(@Validated @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @GetMapping(value = "/user")
    public UserDto findById(@RequestParam(value = "userId") int userId) {
        return userService.findById(userId);
    }

    @PutMapping(value = "/user")
    public UserDto update(@Validated @RequestBody UserDto userDto,
                          @RequestParam(value = "userId") int userId) {
        return userService.update(userDto, userId);
    }

    @DeleteMapping(value = "/user")
    public void delete(@RequestParam(value = "userId") int userId) {
        userService.delete(userId);
    }
}
