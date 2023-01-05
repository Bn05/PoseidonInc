package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.Bid;
import com.nnk.poseidoninc.Model.Dto.BidDto;
import com.nnk.poseidoninc.Service.Implementation.BidServiceImpl;
import com.nnk.poseidoninc.Service.Interface.IBidService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BidControllerAPI {

    private IBidService bidService;

    public BidControllerAPI(BidServiceImpl bidServiceImpl) {
        this.bidService = bidServiceImpl;
    }

    @GetMapping(value = "/bidList")
    public List<BidDto> getBidList() {
        return bidService.findAll();
    }

    @PostMapping(value = "/bid")
    public Bid createBid(@RequestBody @Validated BidDto bidDto) {

        return bidService.addBid(bidDto);
    }

    @GetMapping(value = "/bid")
    public BidDto getBid(@RequestParam(value = "idBid") int idBid) {
        return bidService.findById(idBid);
    }

    @PutMapping(value = "/bid")
    public BidDto updateBid(@RequestBody @Validated BidDto bidDto) {

        return bidService.update(bidDto);
    }

    @DeleteMapping(value = "/bid")
    public void deleteBid(@RequestParam(value = "id") int id) {

        bidService.delete(id);

    }
}
