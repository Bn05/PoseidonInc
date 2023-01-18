package com.nnk.poseidoninc.ControllerWebApp;

import com.nnk.poseidoninc.Model.Dto.TradeDto;
import com.nnk.poseidoninc.Service.Implementation.TradeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TradeControllerWebApp {

    private TradeServiceImpl tradeService;

    public TradeControllerWebApp(TradeServiceImpl tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping(value = "/Trade")
    public String home(Model model) {

        List<TradeDto> tradeDtoList = tradeService.findAll();

        model.addAttribute("tradeDtoList", tradeDtoList);
        model.addAttribute("user", "testauser");

        return "trade/list";
    }

    @GetMapping(value = "/Trade/add")
    public String addTradePage(TradeDto tradeDto) {
        return "trade/add";
    }

    @PostMapping(value = "/Trade/add")
    public String addTrade(@Validated TradeDto tradeDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/trade/add";
        }
        tradeService.create(tradeDto);

        return "redirect:/Trade";
    }

    @GetMapping(value = "/Trade/update/{id}")
    public String updateTradePage(@PathVariable(value = "id") int tradeId,
                                  Model model) {
        TradeDto tradeDto = tradeService.findById(tradeId);

        model.addAttribute("tradeDto", tradeDto);

        return "trade/update";
    }

    @PostMapping(value = "/Trade/update/{id}")
    public String updateTrade(@PathVariable(value = "id") int tradeId,
                              @Validated TradeDto tradeDto,
                              BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "trade/update";
        }
        tradeService.update(tradeDto, tradeId);

        return "redirect:/Trade";
    }

    @GetMapping(value = "/Trade/delete/{id}")
    public String delete(@PathVariable(value = "id") int tradeId) {

        tradeService.delete(tradeId);

        return "redirect:/Trade";
    }
}
