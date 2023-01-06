package com.nnk.poseidoninc.Service.Interface;

import com.nnk.poseidoninc.Model.BidList;
import com.nnk.poseidoninc.Model.Dto.BidListDto;

import java.util.List;


public interface IBidListService {
    List<BidListDto> findAll();

    BidListDto create(BidListDto bidListDto);

    BidListDto findById(int id);

    BidListDto update(BidListDto bidListDto, int bidListId);

    void delete(int id);

    BidList convertBidListDtoToBidList(BidListDto bidListDto);

    BidListDto convertBidListToBidListDto(BidList bidList);
}


