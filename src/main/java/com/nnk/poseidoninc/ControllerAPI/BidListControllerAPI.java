package com.nnk.poseidoninc.ControllerAPI;

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
    public List<BidListDto> findAll() {
        return bidService.findAll();
    }

    @PostMapping(value = "/bidList")
    public BidListDto createBid(@RequestBody @Validated BidListDto bidListDto) {

        return bidService.create(bidListDto);
    }

    @GetMapping(value = "/bidList")
    public BidListDto findById(@RequestParam(value = "idBidList") int idBidList) {
        return bidService.findById(idBidList);
    }

    @PutMapping(value = "/bidList")
    public BidListDto updateById(
            @RequestParam (value = "bidListId") int bidListId,
            @RequestBody @Validated BidListDto bidListDto) {

        return bidService.update(bidListDto, bidListId);
    }

    @DeleteMapping(value = "/bidList")
    public void deleteById(@RequestParam(value = "bidListId") int bidListId) {

        bidService.delete(bidListId);

    }
}
