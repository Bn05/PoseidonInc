package com.nnk.poseidoninc.Service.Interface;

import com.nnk.poseidoninc.Model.Bid;
import com.nnk.poseidoninc.Model.Dto.BidDto;

import java.util.List;


public interface IBidService {
    public List<BidDto> findAll();

    public Bid addBid(BidDto bidDto);

    public BidDto findById(int id);

    public BidDto update(BidDto bidDto);

    public void delete(int id);

    public Bid convertBidDtoToBid(BidDto bidDto);

    public BidDto convertBidToBidDto(Bid bid);
}


