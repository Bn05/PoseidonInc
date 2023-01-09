package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.User;
import com.nnk.poseidoninc.Service.Interface.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserControllerAPI {

    private static final Logger logger = LogManager.getLogger(UserControllerAPI.class);

    private IUserService userService;

    public UserControllerAPI(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "userList")
    public List<UserDto> findAll() {
        logger.info("Request GET /userList");
        List<UserDto> userDtoListValidation = userService.findAll();
        logger.trace("Response to Request : " + userDtoListValidation);
        return userDtoListValidation;
    }

    @PostMapping(value = "/user")
    public UserDto create(@Validated @RequestBody UserDto userDto) {
        logger.info("Request POST /user, RequestBody : " + userDto.toString());
        UserDto userDtoValidation = userService.create(userDto);
        logger.trace("Response to Request : " + userDtoValidation);
        return userDtoValidation;
    }

    @GetMapping(value = "/user")
    public UserDto findById(@RequestParam(value = "userId") int userId) {
        logger.info("Request GET /user, RequestParam userId = " + userId);
        UserDto userDtoValidation = userService.findById(userId);
        logger.trace("Response to Request : " + userDtoValidation.toString());
        return userDtoValidation;
    }

    @PutMapping(value = "/user")
    public UserDto update(@Validated @RequestBody UserDto userDto,
                          @RequestParam(value = "userId") int userId) {
        logger.info("Request PUT /user, RequestParam userId = " + userId + " || RequestBody : " + userDto.toString());
        UserDto userDtoValidation = userService.update(userDto, userId);
        logger.trace("Response to Request : " + userDtoValidation.toString());
        return userDtoValidation;
    }

    @DeleteMapping(value = "/user")
    public void delete(@RequestParam(value = "userId") int userId) {
        logger.info("Request DELETE /user, RequestParam userId = " + userId);
        userService.delete(userId);
        logger.trace("Delete Validated");
    }
}
