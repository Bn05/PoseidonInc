package com.nnk.poseidoninc.IT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Model.Dto.TradeDto;
import com.nnk.poseidoninc.Model.Trade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TradeIT {

    @Autowired
    MockMvc mockMvc;

    TradeDto tradeDto1 = new TradeDto();
    TradeDto tradeDto1NoId = new TradeDto();
    TradeDto tradeDto2 = new TradeDto();
    TradeDto tradeDto2NoId = new TradeDto();
    TradeDto tradeDtoUpdate = new TradeDto();
    List<TradeDto> tradeDtoList = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();
    String tradeDto1Json;
    String tradeDto1NoIdJson;
    String tradeDto2Json;
    String tradeDto2NoIdJson;
    String tradeDtoUpdateJson;
    String tradeDtoListJson;

    @BeforeAll
    void buildTest() throws JsonProcessingException {

        tradeDto1.setTradeId(1);
        tradeDto1.setAccount("accountTest1");
        tradeDto1.setType("typeTest1");
        tradeDto1.setBuyQuantity(5);

        tradeDto1NoId.setAccount("accountTest1");
        tradeDto1NoId.setType("typeTest1");
        tradeDto1NoId.setBuyQuantity(5);

        tradeDto2.setTradeId(2);
        tradeDto2.setAccount("accountTest2");
        tradeDto2.setType("typeTest2");
        tradeDto2.setBuyQuantity(6);

        tradeDto2NoId.setAccount("accountTest2");
        tradeDto2NoId.setType("typeTest2");
        tradeDto2NoId.setBuyQuantity(6);

        tradeDtoList.add(tradeDto1);
        tradeDtoList.add(tradeDto2);

        tradeDtoUpdate.setTradeId(1);
        tradeDtoUpdate.setAccount("accountTestUpdate");
        tradeDtoUpdate.setType("typeTestUpdate");
        tradeDtoUpdate.setBuyQuantity(7);

        tradeDto1Json = objectMapper.writeValueAsString(tradeDto1);
        tradeDto1NoIdJson = objectMapper.writeValueAsString(tradeDto1NoId);
        tradeDto2Json = objectMapper.writeValueAsString(tradeDto2);
        tradeDto2NoIdJson = objectMapper.writeValueAsString(tradeDto2NoId);
        tradeDtoUpdateJson = objectMapper.writeValueAsString(tradeDtoUpdate);
        tradeDtoListJson = objectMapper.writeValueAsString(tradeDtoList);
    }

    @Test
    public void tradeIT() throws Exception {

        //create trade
        mockMvc.perform(post("/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tradeDto1NoIdJson))
                .andExpect(content().json(tradeDto1Json))
                .andExpect(status().isOk());

        //findById
        mockMvc.perform(get("/trade")
                        .param("tradeId", "1"))
                .andExpect(content().json(tradeDto1Json))
                .andExpect(status().isOk());

        //add trade
        mockMvc.perform(post("/trade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tradeDto2NoIdJson))
                .andExpect(content().json(tradeDto2Json))
                .andExpect(status().isOk());

        //find all trade
        mockMvc.perform(get("/tradeList"))
                .andExpect(content().json(tradeDtoListJson))
                .andExpect(status().isOk());

        //update
        mockMvc.perform(put("/trade")
                        .param("tradeId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tradeDtoUpdateJson))
                .andExpect(content().json(tradeDtoUpdateJson))
                .andExpect(status().isOk());

        //verify update with findById
        mockMvc.perform(get("/trade")
                        .param("tradeId", "1"))
                .andExpect(content().json(tradeDtoUpdateJson))
                .andExpect(status().isOk());

        //delete
        mockMvc.perform(delete("/trade")
                        .param("tradeId", "1"))
                .andExpect(status().isOk());

        //verify delete with findById
        mockMvc.perform(get("/trade")
                        .param("tradeId", "1"))
                .andExpect(status().isNotFound());


    }
}
