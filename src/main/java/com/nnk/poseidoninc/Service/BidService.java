package com.nnk.poseidoninc.Service;

import com.nnk.poseidoninc.Exeption.BidNotFoundException;
import com.nnk.poseidoninc.Model.Bid;
import com.nnk.poseidoninc.Model.Dto.BidDto;
import com.nnk.poseidoninc.Repository.BidRepository;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@DynamicUpdate
public class BidService {

    private BidRepository bidRepository;

    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }


    public List<BidDto> findAll() {

        Iterable<Bid> bids = bidRepository.findAll();

        List<BidDto> bidList = new ArrayList<>();

        for (Bid bid : bids) {
            bidList.add(convertBidToBidDto(bid));
        }

        return bidList;
    }

    public Bid addBid(BidDto bidDto) {

        Bid bid = convertBidDtoToBid(bidDto);
        return bidRepository.save(bid);
    }

    public BidDto findById(int id) {
        Optional<Bid> bid = bidRepository.findById(id);

        if (bid.isEmpty()) {
            throw new BidNotFoundException();
        }

        return convertBidToBidDto(bid.get());


    }

    public BidDto update(BidDto bidDto) {

        Optional<Bid> optionalBid = bidRepository.findById(bidDto.getBidId());

        if (optionalBid.isEmpty()) {
            throw new BidNotFoundException();
        }

        Bid bid = optionalBid.get();
        bid.setAccount(bidDto.getAccount());
        bid.setType(bidDto.getType());
        bid.setBidQuantity(bidDto.getBidQuantity());

        return convertBidToBidDto(bidRepository.save(bid));
    }

    public void delete(int id) {

        if (bidRepository.findById(id).isEmpty()) {
            throw new BidNotFoundException();
        }

        bidRepository.deleteById(id);
    }

    public Bid convertBidDtoToBid(BidDto bidDto) {

        Bid bid = new Bid();
        bid.setAccount(bidDto.getAccount());
        bid.setType(bidDto.getType());
        bid.setBidQuantity(bidDto.getBidQuantity());

        return bid;
    }

    public BidDto convertBidToBidDto(Bid bid) {
        BidDto bidDto = new BidDto();

        bidDto.setBidId(bid.getBidId());
        bidDto.setAccount(bid.getAccount());
        bidDto.setType(bid.getType());
        bidDto.setBidQuantity(bid.getBidQuantity());

        return bidDto;
    }
}
