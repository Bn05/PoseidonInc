package com.nnk.poseidoninc.UT.ControllerAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Controller.ControllerAPI.TradeControllerAPI;
import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.TradeDto;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.Trade;
import com.nnk.poseidoninc.Security.TokenService;
import com.nnk.poseidoninc.Service.Implementation.TradeServiceImpl;
import com.nnk.poseidoninc.Service.Implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TradeControllerAPITest {

    @InjectMocks
    TradeControllerAPI tradeControllerAPI;

    @MockBean
    TradeServiceImpl tradeServiceMock;

    @MockBean
    UserServiceImpl userServiceMock;

    @Autowired
    TokenService tokenService;

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

    UserDto userDto1 = new UserDto();

    String token;

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

        userDto1.setUserId(1);
        userDto1.setUserName("user");
        userDto1.setPassword("Password1234!");
        userDto1.setFullName("fullnameTest1");
        userDto1.setRole("ADMIN");

        token = tokenService.generateToken(new UsernamePasswordAuthenticationToken("test", "Password1234!"));

    }

    @Test
    void findAll() throws Exception {
        when(tradeServiceMock.findAll()).thenReturn(tradeDtoList);
        when(userServiceMock.getCurrentUser(any())).thenReturn(userDto1);

        mockMvc.perform(get("/api/tradeList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(content().json(tradeDtoListJson))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        when(tradeServiceMock.create(tradeDto1)).thenReturn(tradeDto1);

        mockMvc.perform(post("/api/trade")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tradeDto1Json))
                .andExpect(content().json(tradeDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void createBadRequest() throws Exception {
        when(tradeServiceMock.create(tradeDto1)).thenReturn(tradeDto1);

        mockMvc.perform(post("/api/trade")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tradeDtoListJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findById() throws Exception {
        when(tradeServiceMock.findById(1)).thenReturn(tradeDto1);

        mockMvc.perform(get("/api/trade")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("tradeId", "1"))
                .andExpect(content().json(tradeDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void findByIdBadParam() throws Exception {
        when(tradeServiceMock.findById(1)).thenReturn(tradeDto1);

        mockMvc.perform(get("/api/trade")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("tradeId", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByIdNotFoundException() throws Exception {
        when(tradeServiceMock.findById(1)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/trade")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("tradeId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        when(tradeServiceMock.update(tradeDto1, 1)).thenReturn(tradeDto1);

        mockMvc.perform(put("/api/trade")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("tradeId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tradeDto1Json))
                .andExpect(content().json(tradeDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void updateBadParam() throws Exception {

        mockMvc.perform(put("/api/trade")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("tradeId", "A")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tradeDto1Json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadBody() throws Exception {

        mockMvc.perform(put("/api/trade")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("tradeId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tradeDtoListJson))

                .andExpect(status().isBadRequest());
    }

    @Test
    void updateNotFoundException() throws Exception {
        when(tradeServiceMock.update(tradeDto1, 1)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/trade")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("tradeId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tradeDto1Json))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(tradeServiceMock).delete(1);

        mockMvc.perform(delete("/api/trade")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("tradeId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByIdBadParam() throws Exception {

        mockMvc.perform(delete("/api/trade")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("tradeId", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteByIdNotFoundException() throws Exception {
        doThrow(NotFoundException.class).when(tradeServiceMock).delete(1);

        mockMvc.perform(delete("/api/trade")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("tradeId", "1"))
                .andExpect(status().isNotFound());
    }
}