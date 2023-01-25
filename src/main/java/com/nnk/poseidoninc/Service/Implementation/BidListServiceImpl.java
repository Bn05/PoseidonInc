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
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserServiceImpl userService;

    public BidListServiceImpl(BidListRepository bidListRepository, UserServiceImpl userService) {
        this.bidListRepository = bidListRepository;
        this.userService = userService;
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

        var authentification = SecurityContextHolder.getContext().getAuthentication();
        String creationName = userService.getCurrentUser(authentification).getUserName();


        BidList bidList = convertBidListDtoToBidList(bidListDto);

        bidList.setCreationDate(LocalDate.now());
        bidList.setCreationName(creationName);

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
        var authentification = SecurityContextHolder.getContext().getAuthentication();
        String revisionName = userService.getCurrentUser(authentification).getUserName();

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
        bidList.setRevisionName(revisionName);

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
