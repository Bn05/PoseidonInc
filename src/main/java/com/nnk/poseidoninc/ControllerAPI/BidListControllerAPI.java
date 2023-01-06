package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.BidList;
import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Service.Implementation.BidListListServiceImpl;
import com.nnk.poseidoninc.Service.Interface.IBidListService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BidListControllerAPI {

    private IBidListService bidService;

    public BidListControllerAPI(BidListListServiceImpl bidListServiceImpl) {
        this.bidService = bidListServiceImpl;
    }

    @GetMapping(value = "/bidLists")
    public List<BidListDto> getBidList() {
        return bidService.findAll();
    }

    @PostMapping(value = "/bidList")
    public BidList createBid(@RequestBody @Validated BidListDto bidListDto) {

        return bidService.addBid(bidListDto);
    }

    @GetMapping(value = "/bidList")
    public BidListDto getBid(@RequestParam(value = "idBidList") int idBidList) {
        return bidService.findById(idBidList);
    }

    @PutMapping(value = "/bidList")
    public BidListDto updateBid(
            @RequestParam (value = "bidListId") int bidListId,
            @RequestBody @Validated BidListDto bidListDto) {

        return bidService.update(bidListDto, bidListId);
    }

    @DeleteMapping(value = "/bidList")
    public void deleteBid(@RequestParam(value = "bidListId") int bidListId) {

        bidService.delete(bidListId);

    }
}
