package com.nnk.poseidoninc.Service.Interface;

import com.nnk.poseidoninc.Model.BidList;
import com.nnk.poseidoninc.Model.Dto.BidListDto;

import java.util.List;


public interface IBidListService {
    public List<BidListDto> findAll();

    public BidList addBid(BidListDto bidListDto);

    public BidListDto findById(int id);

    public BidListDto update(BidListDto bidListDto, int bidListId);

    public void delete(int id);

    public BidList convertBidDtoToBid(BidListDto bidListDto);

    public BidListDto convertBidToBidDto(BidList bidList);
}


