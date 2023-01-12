package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Service.Implementation.BidListServiceImpl;
import com.nnk.poseidoninc.Service.Interface.IBidListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BidListControllerAPI {

    private IBidListService bidService;

    private static final Logger logger = LogManager.getLogger(BidListControllerAPI.class);

    public BidListControllerAPI(BidListServiceImpl bidListServiceImpl) {
        this.bidService = bidListServiceImpl;
    }

    @GetMapping(value = "/bidLists")
    public List<BidListDto> findAll() {
        logger.info("Request GET /bidLists");
        List<BidListDto> bidListDtoList = bidService.findAll();
        logger.trace("Response to Request : " + bidListDtoList.toString());
        return bidListDtoList;
    }

    @PostMapping(value = "/bidList")
    public BidListDto createBid(@RequestBody @Validated BidListDto bidListDto) {
        logger.info("Request POST /bidList, BodyRequest = " + bidListDto.toString());
        BidListDto bidListDtoValidation = bidService.create(bidListDto);
        logger.trace("Response to Request : " + bidListDtoValidation.toString());
        return bidListDtoValidation;
    }

    @GetMapping(value = "/bidList")
    public BidListDto findById(@RequestParam(value = "bidListId") int idBidList) {
        logger.info("Request GET /bidList, RequestParam : idBidList = " + idBidList);
        BidListDto bidListDtoValidation = bidService.findById(idBidList);
        logger.trace("Response to Request : " + bidListDtoValidation.toString());
        return bidListDtoValidation;
    }

    @PutMapping(value = "/bidList")
    public BidListDto updateById(
            @RequestParam(value = "bidListId") int bidListId,
            @RequestBody @Validated BidListDto bidListDto) {
        logger.info("Request PUT /bidList, RequestParam bidListId = " + bidListDto + " || RequestBody = " + bidListDto.toString());
        BidListDto bidListDtoValidation = bidService.update(bidListDto, bidListId);
        logger.trace("Response to Request : " + bidListDtoValidation);
        return bidListDtoValidation;
    }

    @DeleteMapping(value = "/bidList")
    public void deleteById(@RequestParam(value = "bidListId") int bidListId) {
        logger.info("Resquest DELETE /bidList, RequestParam bidListId = "+bidListId);
        bidService.delete(bidListId);
        logger.trace("Validation Delete");

    }
}
