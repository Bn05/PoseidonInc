package com.nnk.poseidoninc.Controller.ControllerWebApp;

import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.User;
import com.nnk.poseidoninc.Service.Implementation.BidListServiceImpl;
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
public class BidListControllerWebApp {

    private BidListServiceImpl bidListService;
    private UserServiceImpl userService;

    public BidListControllerWebApp(BidListServiceImpl bidListService, UserServiceImpl userService) {
        this.bidListService = bidListService;
        this.userService = userService;
    }

    @GetMapping(value = "/BidList")
    public String home(Model model, Authentication authentication) {

        UserDto userDto = userService.getCurrentUser(authentication);

        List<BidListDto> bidListDtoList = bidListService.findAll();

        model.addAttribute("user", userDto);
        model.addAttribute("bidListDtoList", bidListDtoList);

        return "bidList/list";
    }


    @GetMapping(value = "/BidList/add")
    public String addBidListPage(BidListDto bidListDto) {

        return "bidList/add";
    }

    @PostMapping(value = "/BidList/add")
    public String addBidList(@Validated BidListDto bidListDto,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/bidList/add";
        }
        bidListService.create(bidListDto);

        return "redirect:/BidList";
    }


    @GetMapping(value = "/BidList/update/{id}")
    public String updatePage(
            @PathVariable(value = "id") int bidListId,
            Model model
    ) {

        BidListDto bidListDto = bidListService.findById(bidListId);

        model.addAttribute("bidListDto", bidListDto);

        return "bidList/update";
    }

    @PostMapping(value = "/BidList/update/{id}")
    public String update(
            @PathVariable(value = "id") int bidListId,
            @Validated BidListDto bidListDto,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "bidList/update";
        }

        bidListService.update(bidListDto, bidListId);
        return "redirect:/BidList";
    }

    @GetMapping(value = "/BidList/delete/{id}")
    public String delete(
            @PathVariable(value = "id") int bidListId
    ) {

        bidListService.delete(bidListId);

        return "redirect:/BidList";
    }
}