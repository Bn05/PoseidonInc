package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.Bid;
import com.nnk.poseidoninc.Model.Dto.BidDto;
import com.nnk.poseidoninc.Service.BidService;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class BidController {

    private BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping(value = "/bidList")
    public List<BidDto> getBidList() {
        return bidService.findAll();
    }

    @PostMapping(value = "/bid")
    public Bid createBid(@RequestBody BidDto bidDto) {

        return bidService.addBid(bidDto);
    }

    @GetMapping(value = "/bid")
    public BidDto getBid(@RequestParam(value = "idBid") int idBid) {
        return bidService.findById(idBid);
    }

    @PutMapping(value = "/bid")
    public BidDto updateBid(@RequestBody BidDto bidDto) {

        return bidService.update(bidDto);
    }

    @DeleteMapping(value = "/bid")
    public void deleteBid(@RequestParam(value = "id") int id) {

        bidService.delete(id);

    }
}
