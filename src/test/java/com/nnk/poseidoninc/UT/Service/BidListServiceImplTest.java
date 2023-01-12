package com.nnk.poseidoninc.UT.Service;

import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.BidList;
import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Repository.BidListRepository;
import com.nnk.poseidoninc.Service.Implementation.BidListServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BidListServiceImplTest {

    @InjectMocks
    BidListServiceImpl bidListListService;

    @Mock
    BidListRepository bidListRepositoryMock;

    BidListDto bidListDto1 = new BidListDto();
    BidListDto bidListDto2 = new BidListDto();
    BidListDto bidListDtoUpdate = new BidListDto();
    BidList bidList1 = new BidList();
    BidList bidList2 = new BidList();
    BidList bidListUpdate = new BidList();


    Optional<BidList> bidListOptional0 = Optional.empty();
    Optional<BidList> bidListOptional1;
    Optional<BidList> bidListOptional2;
    List<BidList> bidListIterable = new ArrayList<>();
    List<BidListDto> bidListDtoList = new ArrayList<>();


    @BeforeAll
    void buildTest() {

        bidList1.setBidId(1);
        bidList1.setAccount("accountTest1");
        bidList1.setType("typeTest1");
        bidList1.setBidQuantity(45d);
        bidListOptional1 = Optional.of(bidList1);

        bidList2.setBidId(2);
        bidList2.setAccount("accountTest2");
        bidList2.setType("typeTest2");
        bidList2.setBidQuantity(50d);
        bidListOptional2 = Optional.of(bidList2);

        bidListIterable.add(bidList1);
        bidListIterable.add(bidList2);


        bidListDto1.setBidListId(1);
        bidListDto1.setAccount("accountTest1");
        bidListDto1.setType("typeTest1");
        bidListDto1.setBidQuantity(45d);

        bidListDto2.setBidListId(2);
        bidListDto2.setAccount("accountTest2");
        bidListDto2.setType("typeTest2");
        bidListDto2.setBidQuantity(50d);


        bidListDtoList.add(bidListDto1);
        bidListDtoList.add(bidListDto2);


        bidListDtoUpdate.setBidListId(1);
        bidListDtoUpdate.setAccount("accountTestUpdate");
        bidListDtoUpdate.setType("typeTestUpdate");
        bidListDtoUpdate.setBidQuantity(50d);

        bidListUpdate.setBidId(1);
        bidListUpdate.setAccount("accountTestUpdate");
        bidListUpdate.setType("typeTestUpdate");
        bidListUpdate.setBidQuantity(50d);
    }

    @Test
    void findAll() {
        when(bidListRepositoryMock.findAll()).thenReturn(bidListIterable);

        List<BidListDto> bidListDtoListResult = bidListListService.findAll();

        verify(bidListRepositoryMock, times(1)).findAll();
        assertEquals(bidListDtoList, bidListDtoListResult);
    }

    @Test
    void create() {

        when(bidListRepositoryMock.save(any())).thenReturn(bidList1);

        BidListDto bidListDtoResult = bidListListService.create(bidListDto1);

        verify(bidListRepositoryMock, times(1)).save(any());
        assertEquals(bidListDto1, bidListDtoResult);
    }

    @Test
    void findByIdExist() {
        when(bidListRepositoryMock.findById(any())).thenReturn(bidListOptional1);

        BidListDto bidListDtoResult = bidListListService.findById(1);


        verify(bidListRepositoryMock, times(1)).findById(1);
        assertEquals(bidListDto1, bidListDtoResult);
    }

    @Test
    void findByIdNoExist() {
        when(bidListRepositoryMock.findById(any())).thenReturn(bidListOptional0);

        boolean errorTest = true;
        try {
            BidListDto bidListDtoResult = bidListListService.findById(0);
        } catch (NotFoundException e) {
            errorTest = false;
        }
        verify(bidListRepositoryMock, times(1)).findById(0);
        assertFalse(errorTest);
    }

    @Test
    void updateExistId() {

        when(bidListRepositoryMock.findById(1)).thenReturn(bidListOptional1);
        when(bidListRepositoryMock.save(any())).thenReturn(bidListUpdate);

        BidListDto bidListDtoResult = bidListListService.update(bidListDtoUpdate, 1);

        verify(bidListRepositoryMock, times(1)).findById(any());
        verify(bidListRepositoryMock, times(1)).save(any());
        assertEquals(bidListDtoUpdate, bidListDtoResult);
    }

    @Test
    void updateNoExistId() {
        when(bidListRepositoryMock.findById(1)).thenReturn(bidListOptional0);

        boolean errorTest = true;

        try {
            BidListDto bidListDtoResult = bidListListService.update(bidListDtoUpdate, 1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(bidListRepositoryMock, times(1)).findById(any());
        verify(bidListRepositoryMock, times(0)).save(any());
        assertFalse(errorTest);

    }

    @Test
    void deleteExistId() {

        when(bidListRepositoryMock.findById(1)).thenReturn(bidListOptional1);
        doNothing().when(bidListRepositoryMock).deleteById(1);

        bidListListService.delete(1);

        verify(bidListRepositoryMock,times(1)).deleteById(1);
    }

    @Test
    void deleteNoExistId() {

        when(bidListRepositoryMock.findById(1)).thenReturn(bidListOptional0);

        boolean errorTest = true;

        try{
            bidListListService.delete(1);
        }catch (NotFoundException e){
            errorTest = false;
        }

        verify(bidListRepositoryMock,times(0)).deleteById(any());
        assertFalse(errorTest);
    }

}