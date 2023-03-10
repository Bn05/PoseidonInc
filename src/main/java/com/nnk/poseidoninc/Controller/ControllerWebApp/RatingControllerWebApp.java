package com.nnk.poseidoninc.Controller.ControllerWebApp;

import com.nnk.poseidoninc.Model.Dto.RatingDto;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Service.Implementation.RatingServiceImpl;
import com.nnk.poseidoninc.Service.Implementation.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RatingControllerWebApp {

    private RatingServiceImpl ratingService;
    private UserServiceImpl userService;

    public RatingControllerWebApp(RatingServiceImpl ratingService, UserServiceImpl userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @GetMapping(value = "/Rating")
    public String home(Model model, Authentication authentication) {

        UserDto userDto = userService.getCurrentUser(authentication);
        List<RatingDto> ratingDtoList = ratingService.findAll();

        model.addAttribute("ratingDtoList", ratingDtoList);
        model.addAttribute("user", userDto);

        return "rating/list";
    }

    @GetMapping(value = "/Rating/add")
    public String addRatingPage(RatingDto ratingDto) {

        return "rating/add";

    }

    @PostMapping(value = "/Rating/add")
    public String addRating(@Validated RatingDto ratingDto,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "rating/add";
        }

        ratingService.create(ratingDto);

        return "redirect:/Rating";
    }

    @GetMapping(value = "Rating/update/{id}")
    public String updateRatingPage(
            @PathVariable(value = "id") int ratingId,
            Model model
    ) {
        RatingDto ratingDto = ratingService.findById(ratingId);

        model.addAttribute("ratingDto", ratingDto);

        return "rating/update";
    }

    @PostMapping(value = "Rating/update/{id}")
    public String updateRating(
            @PathVariable(value = "id") int ratingId,
            @Validated RatingDto ratingDto,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "rating/update";
        }
        ratingService.update(ratingDto, ratingId);

        return "redirect:/Rating";
    }


    @GetMapping(value = "/Rating/delete/{id}")
    public String delete(
            @PathVariable(value = "id") int ratingId
    ) {
        ratingService.delete(ratingId);

        return "redirect:/Rating";
    }

}
