package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.Dto.TradeDto;
import com.nnk.poseidoninc.Service.Interface.ITradeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TradeControllerAPI {

    private static final Logger logger = LogManager.getLogger(TradeControllerAPI.class);

    private ITradeService tradeService;

    public TradeControllerAPI(ITradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping(value = "/tradeList")
    public List<TradeDto> findAll() {
        logger.info("Request GET /trades");
        List<TradeDto> tradeDtoListValidation = tradeService.findAll();
        logger.trace("Response to Request : " + tradeDtoListValidation.toString());
        return tradeDtoListValidation;
    }

    @PostMapping(value = "/trade")
    public TradeDto create(@Validated @RequestBody TradeDto tradeDto) {
        logger.info("Request POST /trade, RequestBody : " + tradeDto.toString());
        TradeDto tradeDtoValidation = tradeService.create(tradeDto);
        logger.trace("Response to Request : " + tradeDtoValidation.toString());
        return tradeDtoValidation;
    }

    @GetMapping(value = "/trade")
    public TradeDto findById(@RequestParam(value = "tradeId") int tradeId) {
        logger.info("Request GET /trade, RequestParam tradeId = " + tradeId);
        TradeDto tradeDtoValidation = tradeService.findById(tradeId);
        logger.trace("Response to Request : " + tradeDtoValidation.toString());
        return tradeDtoValidation;
    }

    @PutMapping(value = "/trade")
    public TradeDto update(@RequestParam(value = "tradeId") int tradeId,
                           @Validated @RequestBody TradeDto tradeDto) {
        logger.info("Request PUT /trade RequestParam tradeId = " + tradeId + " || RequestBody : " + tradeDto.toString());
        TradeDto tradeDtoValidation = tradeService.update(tradeDto, tradeId);
        logger.trace("Response to Request : " + tradeDtoValidation.toString());
        return tradeDtoValidation;
    }

    @DeleteMapping(value = "/trade")
    public void delete(@RequestParam(value = "tradeId") int tradeId) {
        logger.info("Request DELETE /trade, RequestParam = " + tradeId);
        tradeService.delete(tradeId);
        logger.trace("Delete validated");
    }
}
