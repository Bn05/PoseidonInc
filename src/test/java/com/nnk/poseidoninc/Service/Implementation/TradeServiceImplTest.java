package com.nnk.poseidoninc.Service.Implementation;

import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.TradeDto;
import com.nnk.poseidoninc.Model.Trade;
import com.nnk.poseidoninc.Repository.TradeRepository;
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
class TradeServiceImplTest {

    @InjectMocks
    TradeServiceImpl tradeService;

    @Mock
    TradeRepository tradeRepositoryMock;

    Trade trade1 = new Trade();
    Trade trade2 = new Trade();
    Trade tradeUpdate = new Trade();
    TradeDto tradeDto1 = new TradeDto();
    TradeDto tradeDto2 = new TradeDto();
    TradeDto tradeDtoUpdate = new TradeDto();

    Optional<Trade> tradeOptional0 = Optional.empty();
    Optional<Trade> tradeOptional1;
    Optional<Trade> tradeOptional2;
    List<Trade> tradeIterable = new ArrayList<>();
    List<TradeDto> tradeDtoList = new ArrayList<>();

    @BeforeAll
    void buildTest() {

        trade1.setTradeId(1);
        trade1.setAccount("accountTest1");
        trade1.setType("typeTest1");
        trade1.setBuyQuantity(5);
        tradeOptional1 = Optional.of(trade1);

        trade2.setTradeId(2);
        trade2.setAccount("accountTest2");
        trade2.setType("typeTest2");
        trade2.setBuyQuantity(6);
        tradeOptional2 = Optional.of(trade2);

        tradeIterable.add(trade1);
        tradeIterable.add(trade2);

        tradeDto1.setTradeId(1);
        tradeDto1.setAccount("accountTest1");
        tradeDto1.setType("typeTest1");
        tradeDto1.setBuyQuantity(5);

        tradeDto2.setTradeId(2);
        tradeDto2.setAccount("accountTest2");
        tradeDto2.setType("typeTest2");
        tradeDto2.setBuyQuantity(6);

        tradeDtoList.add(tradeDto1);
        tradeDtoList.add(tradeDto2);

        tradeUpdate.setTradeId(1);
        tradeUpdate.setAccount("accountTestUpdate");
        tradeUpdate.setType("typeTestUpdate");
        tradeUpdate.setBuyQuantity(7);

        tradeDtoUpdate.setTradeId(1);
        tradeDtoUpdate.setAccount("accountTestUpdate");
        tradeDtoUpdate.setType("typeTestUpdate");
        tradeDtoUpdate.setBuyQuantity(7);
    }

    @Test
    void findAll() {
        when(tradeRepositoryMock.findAll()).thenReturn(tradeIterable);

        List<TradeDto> tradeDtoListResult = tradeService.findAll();

        verify(tradeRepositoryMock, times(1)).findAll();
        assertEquals(tradeDtoList, tradeDtoListResult);
    }

    @Test
    void create() {
        when(tradeRepositoryMock.save(any())).thenReturn(trade1);

        TradeDto tradeDtoResult = tradeService.create(tradeDto1);

        verify(tradeRepositoryMock, times(1)).save(any());
        assertEquals(tradeDto1, tradeDtoResult);
    }

    @Test
    void findByIdExist() {
        when(tradeRepositoryMock.findById(1)).thenReturn(tradeOptional1);

        TradeDto tradeDtoResult = tradeService.findById(1);

        verify(tradeRepositoryMock, times(1)).findById(1);
        assertEquals(tradeDto1, tradeDtoResult);
    }

    @Test
    void findByIdNoExist() {
        when(tradeRepositoryMock.findById(1)).thenReturn(tradeOptional0);
        boolean errorTest = true;

        try {
            TradeDto tradeDtoResult = tradeService.findById(1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(tradeRepositoryMock, times(1)).findById(1);
        assertFalse(errorTest);
    }

    @Test
    void updateIdExist() {
        when(tradeRepositoryMock.findById(1)).thenReturn(tradeOptional1);
        when(tradeRepositoryMock.save(any())).thenReturn(trade1);

        TradeDto tradeDtoResult = tradeService.update(tradeDto1, 1);

        verify(tradeRepositoryMock, times(1)).findById(1);
        verify(tradeRepositoryMock, times(1)).save(any());
        assertEquals(tradeDto1, tradeDtoResult);
    }

    @Test
    void updateIdNoExist() {
        when(tradeRepositoryMock.findById(1)).thenReturn(tradeOptional0);
        boolean errorTest = true;

        try {

            TradeDto tradeDtoResult = tradeService.update(tradeDto1, 1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(tradeRepositoryMock, times(1)).findById(1);
        verify(tradeRepositoryMock, times(0)).save(any());
        assertFalse(errorTest);
    }

    @Test
    void deleteIdExist() {
        when(tradeRepositoryMock.findById(1)).thenReturn(tradeOptional1);
        doNothing().when(tradeRepositoryMock).deleteById(1);

        tradeService.delete(1);

        verify(tradeRepositoryMock, times(1)).findById(1);
        verify(tradeRepositoryMock, times(1)).deleteById(1);
    }

    @Test
    void deleteIdNoExist() {
        when(tradeRepositoryMock.findById(1)).thenReturn(tradeOptional0);
        boolean errorTest = true;

        try {
            tradeService.delete(1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(tradeRepositoryMock, times(1)).findById(1);
        verify(tradeRepositoryMock, times(0)).deleteById(1);
        assertFalse(errorTest);
    }
}