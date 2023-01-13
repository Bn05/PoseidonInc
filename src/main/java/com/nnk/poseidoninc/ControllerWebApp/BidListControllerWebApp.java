package com.nnk.poseidoninc.ControllerWebApp;

import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Service.Implementation.BidListServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping(value = "/BidList/delete/{id}")
    public String delete(
           @PathVariable(value = "id") int bidListId
    ) {

        bidListService.delete(bidListId);

        return "redirect:/BidList";
    }
}