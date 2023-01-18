package com.nnk.poseidoninc.UT.ControllerWebApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.ControllerWebApp.TradeControllerWebApp;
import com.nnk.poseidoninc.Model.Dto.TradeDto;
import com.nnk.poseidoninc.Model.Trade;
import com.nnk.poseidoninc.Service.Implementation.TradeServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TradeControllerWebAppTest {

    @InjectMocks
    TradeControllerWebApp tradeControllerWebApp;

    @MockBean
    TradeServiceImpl tradeServiceMock;

    @Mock
    TradeServiceImpl tradeService;

    @Mock
    Model model;

    @Autowired
    MockMvc mockMvc;

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

    ObjectMapper objectMapper = new ObjectMapper();
    String tradeDto1Json;
    String tradeDtoListJson;

    @BeforeAll
    void buildTest() throws JsonProcessingException {

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

        tradeDto1Json = objectMapper.writeValueAsString(tradeDto1);
        tradeDtoListJson = objectMapper.writeValueAsString(tradeDtoList);
    }

    @Test
    void home() throws Exception {

        when(tradeServiceMock.findAll()).thenReturn(tradeDtoList);

        mockMvc.perform(get("/Trade"))
                .andExpect(model().attribute("tradeDtoList", tradeDtoList))
                .andExpect(status().isOk());
    }

    @Test
    void addTradePage() throws Exception {
        mockMvc.perform(get("/Trade/add"))
                .andExpect(view().name("trade/add"))
                .andExpect(status().isOk());
    }

    @Test
    void addTrade() throws Exception {
        when(tradeServiceMock.create(any())).thenReturn(tradeDto1);

        mockMvc.perform(post("/Trade/add")
                        .param("account", "accountTest")
                        .param("type", "typeTest")
                        .param("buyQuantity", "7"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updateTradePage() {
        when(tradeService.findById(anyInt())).thenReturn(tradeDto1);

        model.addAttribute("tradeDto", tradeDto1);

        String test = tradeControllerWebApp.updateTradePage(1, model);

        assertEquals("trade/update", test);
    }

    @Test
    void updateTrade() throws Exception {
        when(tradeServiceMock.update(any(), anyInt())).thenReturn(tradeDtoUpdate);

        mockMvc.perform(post("/Trade/update/1")
                        .param("account", "accountTest")
                        .param("type", "typeTest")
                        .param("buyQuantity", "7"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(tradeServiceMock).delete(1);

        mockMvc.perform(get("/Trade/delete/1"))
                .andExpect(status().is3xxRedirection());
    }
}