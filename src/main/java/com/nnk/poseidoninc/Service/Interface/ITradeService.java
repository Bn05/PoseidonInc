package com.nnk.poseidoninc.Service.Interface;

import com.nnk.poseidoninc.Model.BidList;
import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Model.Dto.TradeDto;
import com.nnk.poseidoninc.Model.Trade;

import java.util.List;

public interface ITradeService {

    List<TradeDto> findAll();

    TradeDto create(TradeDto tradeDto);

    TradeDto findById(int tradeId);

    TradeDto update(TradeDto tradeDto, int tradeId);

    void delete(int tradeId);

    TradeDto convertTradeToTradeDto(Trade trade);

    Trade convertTradeDtoToTrade(TradeDto tradeDto);


}
