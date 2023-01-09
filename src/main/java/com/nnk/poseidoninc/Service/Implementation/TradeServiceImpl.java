package com.nnk.poseidoninc.Service.Implementation;

import com.nnk.poseidoninc.ControllerAPI.TradeControllerAPI;
import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.TradeDto;
import com.nnk.poseidoninc.Model.Trade;
import com.nnk.poseidoninc.Repository.TradeRepository;
import com.nnk.poseidoninc.Service.Interface.ITradeService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@DynamicUpdate
public class TradeServiceImpl implements ITradeService {

    private static final Logger logger = LogManager.getLogger(TradeServiceImpl.class);

    private TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<TradeDto> findAll() {

        Iterable<Trade> tradeIterable = tradeRepository.findAll();

        List<TradeDto> tradeDtoList = new ArrayList<>();

        for (Trade trade : tradeIterable) {
            tradeDtoList.add(convertTradeToTradeDto(trade));
        }

        return tradeDtoList;
    }

    @Override
    public TradeDto create(TradeDto tradeDto) {

        Trade trade = convertTradeDtoToTrade(tradeDto);

        trade.setCreationDate(LocalDate.now());
        // TODO : AutomaticName -> AutomaticNameCreation (when spring Security on)
        trade.setCreationName("AutomaticNameCreation");

        trade = tradeRepository.save(trade);

        return convertTradeToTradeDto(trade);
    }

    @Override
    public TradeDto findById(int tradeId) {

        Optional<Trade> tradeOptional = tradeRepository.findById(tradeId);

        if (tradeOptional.isEmpty()) {
            logger.warn("NotFoundBidListWithThisId");
            throw new NotFoundException();
        }

        return convertTradeToTradeDto(tradeOptional.get());
    }

    @Override
    public TradeDto update(TradeDto tradeDto, int tradeId) {
        Optional<Trade> tradeOptional = tradeRepository.findById(tradeId);

        if (tradeOptional.isEmpty()) {
            logger.warn("NotFoundBidListWithThisId");
            throw new NotFoundException();
        }

        Trade trade = tradeOptional.get();
        trade.setAccount(tradeDto.getAccount());
        trade.setType(tradeDto.getType());
        trade.setBuyQuantity(tradeDto.getBuyQuantity());

        trade.setRevisionDate(LocalDate.now());
        // TODO : AutomaticName -> AutomaticNameRevision (when spring Security on)
        trade.setRevisionName("AutomaticNameRevision");

        trade = tradeRepository.save(trade);

        return convertTradeToTradeDto(trade);
    }

    @Override
    public void delete(int tradeId) {
        Optional<Trade> tradeOptional = tradeRepository.findById(tradeId);

        if (tradeOptional.isEmpty()) {
            logger.warn("NotFoundBidListWithThisId");
            throw new NotFoundException();
        }

        tradeRepository.deleteById(tradeId);
    }

    @Override
    public TradeDto convertTradeToTradeDto(Trade trade) {
        TradeDto tradeDto = new TradeDto();

        tradeDto.setTradeId(trade.getTradeId());
        tradeDto.setAccount(trade.getAccount());
        tradeDto.setType(trade.getType());
        tradeDto.setBuyQuantity(trade.getBuyQuantity());

        return tradeDto;
    }

    @Override
    public Trade convertTradeDtoToTrade(TradeDto tradeDto) {
        Trade trade = new Trade();

        trade.setAccount(tradeDto.getAccount());
        trade.setType(tradeDto.getType());
        trade.setBuyQuantity(tradeDto.getBuyQuantity());

        return trade;
    }
}
