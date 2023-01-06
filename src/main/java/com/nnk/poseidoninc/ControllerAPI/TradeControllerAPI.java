package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.Dto.TradeDto;
import com.nnk.poseidoninc.Service.Interface.ITradeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TradeControllerAPI {

    private ITradeService tradeService;

    public TradeControllerAPI(ITradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping(value = "/trades")
    public List<TradeDto> findAll() {
        return tradeService.findAll();
    }

    @PostMapping(value = "/trade")
    public TradeDto create(@Validated @RequestBody TradeDto tradeDto) {
        return tradeService.create(tradeDto);
    }

    @GetMapping(value = "/trade")
    public TradeDto findById(int tradeId) {
        return tradeService.findById(tradeId);
    }

    @PutMapping(value = "/trade")
    public TradeDto update(@RequestParam(value = "tradeId") int tradeId,
                           @Validated @RequestBody TradeDto tradeDto) {

        return tradeService.update(tradeDto, tradeId);
    }

    @DeleteMapping(value = "/trade")
    public void delete(int tradeId) {
        tradeService.delete(tradeId);
    }
}
