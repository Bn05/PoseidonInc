package com.nnk.poseidoninc.Controller.ControllerAPI;

import com.nnk.poseidoninc.Exception.BadParamException;
import com.nnk.poseidoninc.Exception.NotFoundException;

import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Service.Interface.IUserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;

@RestController
public class UserControllerAPI {

    private static final Logger logger = LogManager.getLogger(UserControllerAPI.class);

    private IUserService userService;

    public UserControllerAPI(IUserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "${apiPrefix}/userList")
    public List<UserDto> findAll(Authentication authentication) {


        logger.info("Request GET /userList");
        List<UserDto> userDtoListValidation = userService.findAll();
        logger.trace("Response to Request : " + userDtoListValidation);
        return userDtoListValidation;
    }

    @PostMapping(value = "${apiPrefix}/user")
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        logger.info("Request POST /user, RequestBody : " + userDto.toString());
        UserDto userDtoValidation = userService.create(userDto);
        logger.trace("Response to Request : " + userDtoValidation.toString());
        return userDtoValidation;
    }

    @GetMapping(value = "${apiPrefix}/user")
    public UserDto findById(@RequestParam(value = "userId") int userId) {
        logger.info("Request GET /user, RequestParam userId = " + userId);
        UserDto userDtoValidation = userService.findById(userId);
        logger.trace("Response to Request : " + userDtoValidation.toString());
        return userDtoValidation;
    }

    @PutMapping(value = "${apiPrefix}/user")
    public UserDto update(@RequestBody @Validated UserDto userDto,
                          @RequestParam(value = "userId") int userId
    ) {
        logger.info("Request PUT /user, RequestParam userId = " + userId + " || RequestBody : " + userDto.toString());
        UserDto userDtoValidation = userService.update(userDto, userId);
        logger.trace("Response to Request : " + userDtoValidation.toString());
        return userDtoValidation;
    }

    @DeleteMapping(value = "${apiPrefix}/user")
    public void delete(@RequestParam(value = "userId") int userId) {
        logger.info("Request DELETE /user, RequestParam userId = " + userId);
        userService.delete(userId);
        logger.trace("Delete Validated");
    }
}
