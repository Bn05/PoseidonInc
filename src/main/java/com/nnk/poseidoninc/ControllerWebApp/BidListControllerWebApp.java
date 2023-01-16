package com.nnk.poseidoninc.ControllerWebApp;

import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Service.Implementation.BidListServiceImpl;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;


@Controller
public class BidListControllerWebApp {

    private BidListServiceImpl bidListService;

    public BidListControllerWebApp(BidListServiceImpl bidListService) {
        this.bidListService = bidListService;
    }

    @GetMapping(value = "/BidList")
    public String home(Model model) {

        List<BidListDto> bidListDtoList = bidListService.findAll();

        model.addAttribute("user", "userTESTA");
        model.addAttribute("bidListDtoList", bidListDtoList);

        return "bidList/list";
    }


    @GetMapping(value = "/BidList/add")
    public String addBidListPage(BidListDto bidListDto) {

        return "bidList/add";
    }

    @PostMapping(value = "/BidList/add")
    public String addBidList(@Validated BidListDto bidListDto) {
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
            BidListDto bidListDto
    ) {

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