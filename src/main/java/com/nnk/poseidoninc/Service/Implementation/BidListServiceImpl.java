package com.nnk.poseidoninc.Service.Implementation;

import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.BidList;
import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Repository.BidListRepository;
import com.nnk.poseidoninc.Service.Interface.IBidListService;
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
public class BidListServiceImpl implements IBidListService {

    private static final Logger logger = LogManager.getLogger(BidListServiceImpl.class);

    private BidListRepository bidListRepository;

    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }


   @Override
    public List<BidListDto> findAll() {

        Iterable<BidList> bids = bidListRepository.findAll();
        List<BidListDto> bidList = new ArrayList<>();

        for (BidList bid : bids) {
            bidList.add(convertBidListToBidListDto(bid));
        }

        return bidList;
    }

    @Override
    public BidListDto create(BidListDto bidListDto) {

        BidList bidList = convertBidListDtoToBidList(bidListDto);

        bidList.setCreationDate(LocalDate.now());
        // TODO : AutomaticName -> authentificationNameCreation (when spring Security on)
        bidList.setCreationName("AutomaticNameCreation");

        return (convertBidListToBidListDto(bidListRepository.save(bidList)));
    }

    @Override
    public BidListDto findById(int id) {
        Optional<BidList> bid = bidListRepository.findById(id);

        if (bid.isEmpty()) {
            logger.warn("NotFoundBidListWithThisId");
            throw new NotFoundException();
        }

        return convertBidListToBidListDto(bid.get());


    }

    @Override
    public BidListDto update(BidListDto bidListDto, int bidListId) {

        Optional<BidList> optionalBid = bidListRepository.findById(bidListId);

        if (optionalBid.isEmpty()) {
            logger.warn("NotFoundBidListWithThisId");
            throw new NotFoundException();
        }

        BidList bidList = optionalBid.get();
        bidList.setAccount(bidListDto.getAccount());
        bidList.setType(bidListDto.getType());
        bidList.setBidQuantity(bidListDto.getBidQuantity());

        bidList.setRevisionDate(LocalDate.now());
        // TODO : AutomaticName -> AutomaticNameRevision (when spring Security on)
        bidList.setRevisionName("AutomaticNameRevision");


        return convertBidListToBidListDto(bidListRepository.save(bidList));
    }

    @Override
    public void delete(int id) {

        if (bidListRepository.findById(id).isEmpty()) {
            logger.warn("NotFoundBidListWithThisId");
            throw new NotFoundException();
        }

        bidListRepository.deleteById(id);
    }

    @Override
    public BidList convertBidListDtoToBidList(BidListDto bidListDto) {
        BidList bidList = new BidList();

        bidList.setAccount(bidListDto.getAccount());
        bidList.setType(bidListDto.getType());
        bidList.setBidQuantity(bidListDto.getBidQuantity());

        return bidList;
    }

    @Override
    public BidListDto convertBidListToBidListDto(BidList bidList) {
        BidListDto bidListDto = new BidListDto();

        bidListDto.setBidListId(bidList.getBidId());
        bidListDto.setAccount(bidList.getAccount());
        bidListDto.setType(bidList.getType());
        bidListDto.setBidQuantity(bidList.getBidQuantity());

        return bidListDto;
    }
}