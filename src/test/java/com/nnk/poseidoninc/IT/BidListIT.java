package com.nnk.poseidoninc.IT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Security.TokenService;
import com.nnk.poseidoninc.Service.Implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BidListIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TokenService tokenService;

    @MockBean
    UserServiceImpl userService;


    BidListDto bidListDto1 = new BidListDto();
    BidListDto bidListDto1NoId = new BidListDto();

    BidListDto bidListDto2 = new BidListDto();
    BidListDto bidListDto2NoId = new BidListDto();

    BidListDto bidListDtoUpdate = new BidListDto();
    List<BidListDto> bidListDtoList = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();
    String bidListDto1Json;
    String bidListDto1NoIdJson;
    String bidListDto2Json;
    String bidListDto2NoIdJson;
    String bidListDtoUpdateJson;
    String bidListDtoListJson;

    String token;

    UserDto userDto1 = new UserDto();

    @BeforeAll
    void buildTest() throws JsonProcessingException {


        bidListDto1NoId.setAccount("accountTest1");
        bidListDto1NoId.setType("typeTest1");
        bidListDto1NoId.setBidQuantity(45d);


        bidListDto1.setBidListId(1);
        bidListDto1.setAccount("accountTest1");
        bidListDto1.setType("typeTest1");
        bidListDto1.setBidQuantity(45d);


        bidListDto2.setBidListId(2);
        bidListDto2.setAccount("accountTest2");
        bidListDto2.setType("typeTest2");
        bidListDto2.setBidQuantity(50d);

        bidListDto2NoId.setAccount("accountTest2");
        bidListDto2NoId.setType("typeTest2");
        bidListDto2NoId.setBidQuantity(50d);


        bidListDtoList.add(bidListDto1);
        bidListDtoList.add(bidListDto2);

        bidListDtoUpdate.setBidListId(1);
        bidListDtoUpdate.setAccount("accountTestUpdate");
        bidListDtoUpdate.setType("typeTestUpdate");
        bidListDtoUpdate.setBidQuantity(70d);


        bidListDto1Json = objectMapper.writeValueAsString(bidListDto1);
        bidListDto1NoIdJson = objectMapper.writeValueAsString(bidListDto1NoId);

        bidListDto2Json = objectMapper.writeValueAsString(bidListDto2);
        bidListDto2NoIdJson = objectMapper.writeValueAsString(bidListDto2NoId);

        bidListDtoUpdateJson = objectMapper.writeValueAsString(bidListDtoUpdate);
        bidListDtoListJson = objectMapper.writeValueAsString(bidListDtoList);

        userDto1.setUserId(1);
        userDto1.setUserName("user");
        userDto1.setPassword("Password1234!");
        userDto1.setFullName("fullnameTest1");
        userDto1.setRole("ADMIN");

        token = tokenService.generateToken(new UsernamePasswordAuthenticationToken("test", "Password1234!"));
    }

    @Test
    public void bidListIT() throws Exception {

        when(userService.getCurrentUser(any())).thenReturn(userDto1);

        //add bidList
        mockMvc.perform(post("/api/bidList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bidListDto1NoIdJson))
                .andExpect(content().json(bidListDto1Json))
                .andExpect(status().isOk());

        //findById bidList
        mockMvc.perform(get("/api/bidList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("bidListId", "1"))
                .andExpect(content().json(bidListDto1Json))
                .andExpect(status().isOk());

        //add new bidList
        mockMvc.perform(post("/api/bidList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bidListDto2NoIdJson))
                .andExpect(content().json(bidListDto2Json))
                .andExpect(status().isOk());

        //verify all bidList
        mockMvc.perform(get("/api/bidLists")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(content().json(bidListDtoListJson))
                .andExpect(status().isOk());

        //update bidList
        mockMvc.perform(put("/api/bidList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("bidListId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bidListDtoUpdateJson))
                .andExpect(content().json(bidListDtoUpdateJson))
                .andExpect(status().isOk());

        //verify update bidList with findById
        mockMvc.perform(get("/api/bidList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("bidListId", "1"))
                .andExpect(content().json(bidListDtoUpdateJson))
                .andExpect(status().isOk());

        //delete bidList
        mockMvc.perform(delete("/api/bidList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("bidListId", "1"))
                .andExpect(status().isOk());

        //verify delete bidList with findByID
        mockMvc.perform(get("/api/bidList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("bidListId", "1"))
                .andExpect(status().isNotFound());
    }


}
