package com.nnk.poseidoninc.Controller.ControllerWebApp;

import com.nnk.poseidoninc.Model.Dto.AdvanceInfo;
import com.nnk.poseidoninc.Model.Dto.BasicInfo;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Service.Implementation.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserControllerWebApp {

    private UserServiceImpl userService;

    public UserControllerWebApp(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/User")
    public String home(Model model) {
        List<UserDto> userDtoList = userService.findAll();

        model.addAttribute("userDtoList", userDtoList);

        return "user/list";
    }

    @GetMapping(value = "/User/add")
    public String addUserPage(UserDto userDto) {
        return "user/add";
    }

    @PostMapping(value = "/User/add")
    public String addUser(@Validated(BasicInfo.class) UserDto userDto,
                          BindingResult result) {

        userDto.setRole("USER");

        if (result.hasErrors()) {
            return "/user/add";
        }

        userService.create(userDto);

        return "redirect:/User";
    }

    @GetMapping(value = "/User/update/{id}")
    public String updateUserPage(@PathVariable(value = "id") int userId,
                                 Model model) {
        UserDto userDto = userService.findById(userId);

        model.addAttribute("userDto", userDto);

        return "user/update";
    }

    @PostMapping(value = "/User/update/{id}")
    public String updateUser(@PathVariable(value = "id") int userId,
                             @Validated({AdvanceInfo.class}) UserDto userDto,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/user/update";
        }

        if(userDto.getPassword().isEmpty()){
            String password = userService.findById(userId).getPassword();
            userDto.setPassword(password);
        }
        userService.update(userDto, userId);

        return "redirect:/User";
    }


    @GetMapping(value = "/User/delete/{id}")
    public String delete(@PathVariable(value = "id") int userId) {

        userService.delete(userId);

        return "redirect:/User";
    }
}
