package com.nnk.poseidoninc.Controller.ControllerAPI;

import com.nnk.poseidoninc.Exception.BadParamException;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Security.ValidPassword.PasswordConstraintValidator;
import com.nnk.poseidoninc.Security.ValidPassword.ValidPassword;
import com.nnk.poseidoninc.Service.Interface.IUserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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


    @GetMapping(value = "${apiPrefix}/userList")
    public List<UserDto> findAll(Authentication authentication) {


        logger.info("Request GET /userList");
        List<UserDto> userDtoListValidation = userService.findAll();
        logger.trace("Response to Request : " + userDtoListValidation);
        return userDtoListValidation;
    }

    @PostMapping(value = "${apiPrefix}/user")
    public UserDto create(@RequestBody @Validated UserDto userDto) {
        logger.info("Request POST /user, RequestBody : " + userDto.toString());
        UserDto userDtoValidation = userService.create(userDto);
        logger.trace("Response to Request : " + userDtoValidation.toString());
        return userDtoValidation;
    }

    @GetMapping(value = "${apiPrefix}/user")
    public UserDto findById(@RequestParam(required = false, value = "userId") int userId, Authentication authentication) {
        logger.info("Request GET /user, RequestParam userId = " + userId);
        UserDto userDtoValidation = userService.findById(userId);
        logger.trace("Response to Request : " + userDtoValidation.toString());
        return userDtoValidation;
    }

    @GetMapping(value = "${apiPrefix}/user/me")
    public UserDto findMe(Authentication authentication) {
        logger.info("Request GET /user/me");
        UserDto userDto = userService.getCurrentUser(authentication);
        logger.trace("Response to Request : " + userDto.toString());
        return userDto;

    }

    @PutMapping(value = "${apiPrefix}/user")
    public UserDto update(@RequestParam(value = "userId") int userId,
                          @RequestBody @Valid UserDto userDto, BindingResult bindingResult,
                          Authentication authentication

    ) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if (!fieldError.getField().equals("password")) {
                throw new BadParamException();
            }
            if (userDto.getPassword() != null) {
                throw new BadParamException();
            }
        }

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
