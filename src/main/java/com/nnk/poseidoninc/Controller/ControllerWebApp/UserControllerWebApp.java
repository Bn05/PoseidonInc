package com.nnk.poseidoninc.Controller.ControllerWebApp;


import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Service.Implementation.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserControllerWebApp {

    private UserServiceImpl userService;

    public UserControllerWebApp(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/User")
    public String home(Model model, Authentication authentication) {
        UserDto userDto = userService.getCurrentUser(authentication);

        List<UserDto> userDtoList = new ArrayList<>();
        if (userDto.getRole().equals("ADMIN")) {
            userDtoList = userService.findAll();
        } else {
            userDtoList.add(userDto);
        }


        model.addAttribute("userDtoList", userDtoList);
        model.addAttribute("user", userDto);

        return "user/list";
    }

    @GetMapping(value = "/User/add")
    public String addUserPage(UserDto userDto, Authentication authentication, Model model) {

        Boolean authenticated = false;
        if (authentication != null) {
            authenticated = true;
        }

        model.addAttribute("authenticated", authenticated);


        return "user/add";
    }

    @PostMapping(value = "/User/add")
    public String addUser(@Validated UserDto userDto,
                          BindingResult result) {

        userDto.setRole("USER");

        if (result.hasErrors()) {
            return "user/add";
        }

        userService.create(userDto);

        return "redirect:/BidList";
    }

    @GetMapping(value = "/User/update/{id}")
    public String updateUserPage(@PathVariable(value = "id") int userId,
                                 Model model,
                                 Authentication authentication) {

        UserDto userDto = userService.findById(userId);

        String role = userService.getCurrentUser(authentication).getRole();
        Boolean admin = false;
        if (role.equals("ADMIN")) {
            admin = true;
        }


        model.addAttribute("userDto", userDto);
        model.addAttribute("admin", admin);

        return "user/update";
    }

    @PostMapping(value = "/User/update/{id}")
    public String updateUser(@PathVariable(value = "id") int userId,
                             @Validated UserDto userDto,
                             BindingResult bindingResult,
                             Authentication authentication) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if (!fieldError.getField().equals("password")) {
                return "user/update";
            }
            if (!userDto.getPassword().isBlank()) {
                return "user/update";
            }
        }
        userService.update(userDto, userId);

        UserDto userDtoSec = userService.getCurrentUser(authentication);

        if (userDtoSec.getRole().equals("ADMIN")) {
            return "redirect:/User";
        }

        return "redirect:/BidList";


    }


    @GetMapping(value = "/User/delete/{id}")
    public String delete(@PathVariable(value = "id") int userId) {

        userService.delete(userId);

        return "redirect:/User";
    }
}
