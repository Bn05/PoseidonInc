package com.nnk.poseidoninc.Service.Implementation;

import com.nnk.poseidoninc.Exception.BidListNotFoundException;
import com.nnk.poseidoninc.Model.BidList;
import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Repository.BidListRepository;
import com.nnk.poseidoninc.Service.Interface.IBidListService;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@DynamicUpdate
public class BidListListServiceImpl implements IBidListService {

    private BidListRepository bidListRepository;

    public BidListListServiceImpl(BidListRepository bidListRepository) {
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
            throw new BidListNotFoundException();
        }

        return convertBidListToBidListDto(bid.get());


    }

    @Override
    public BidListDto update(BidListDto bidListDto, int bidListId) {

        Optional<BidList> optionalBid = bidListRepository.findById(bidListId);

        if (optionalBid.isEmpty()) {
            throw new BidListNotFoundException();
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
            throw new BidListNotFoundException();
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
