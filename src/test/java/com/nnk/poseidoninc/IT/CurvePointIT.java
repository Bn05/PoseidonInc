package com.nnk.poseidoninc.IT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Model.Dto.CurvePointDto;
import com.nnk.poseidoninc.Security.TokenService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CurvePointIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    TokenService tokenService;

    CurvePointDto curvePointDto1 = new CurvePointDto();
    CurvePointDto curvePointDto1NoId = new CurvePointDto();

    CurvePointDto curvePointDto2 = new CurvePointDto();
    CurvePointDto curvePointDto2NoId = new CurvePointDto();

    CurvePointDto curvePointDtoUpdate = new CurvePointDto();
    List<CurvePointDto> curvePointDtoList = new ArrayList<>();


    ObjectMapper objectMapper = new ObjectMapper();
    String curvePointDto1Json;
    String curvePointDto1NoIdJson;
    String curvePointDto2Json;
    String curvePointDto2NoIdJson;
    String curvePointDtoUpdateJson;
    String curvePointDtoListJson;
    String token;


    @BeforeAll
    void buildTest() throws JsonProcessingException {


        curvePointDto1.setCurvePointId(1);
        curvePointDto1.setTerm(11);
        curvePointDto1.setValue(11);

        curvePointDto1NoId.setTerm(11);
        curvePointDto1NoId.setValue(11);

        curvePointDto2.setCurvePointId(2);
        curvePointDto2.setTerm(22);
        curvePointDto2.setValue(22);

        curvePointDto2NoId.setTerm(22);
        curvePointDto2NoId.setValue(22);


        curvePointDtoList.add(curvePointDto1);
        curvePointDtoList.add(curvePointDto2);


        curvePointDtoUpdate.setCurvePointId(1);
        curvePointDtoUpdate.setTerm(33);
        curvePointDtoUpdate.setValue(33);


        curvePointDto1Json = objectMapper.writeValueAsString(curvePointDto1);
        curvePointDto1NoIdJson = objectMapper.writeValueAsString(curvePointDto1NoId);
        curvePointDto2Json = objectMapper.writeValueAsString(curvePointDto2);
        curvePointDto2NoIdJson = objectMapper.writeValueAsString(curvePointDto2NoId);
        curvePointDtoUpdateJson = objectMapper.writeValueAsString(curvePointDtoUpdate);
        curvePointDtoListJson = objectMapper.writeValueAsString(curvePointDtoList);

        token = tokenService.generateToken(new UsernamePasswordAuthenticationToken("test", "Password1234!"));
    }


    @Test
    public void curvePointIT() throws Exception {

        //create curvePoint
        mockMvc.perform(post("/api/curvePoint")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(curvePointDto1NoIdJson))
                .andExpect(content().json(curvePointDto1Json))
                .andExpect(status().isOk());

        //findById curvePoint
        mockMvc.perform(get("/api/curvePoint")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("curvePointId", "1"))
                .andExpect(content().json(curvePointDto1Json))
                .andExpect(status().isOk());

        //add new curvePoint
        mockMvc.perform(post("/api/curvePoint")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(curvePointDto2NoIdJson))
                .andExpect(content().json(curvePointDto2Json))
                .andExpect(status().isOk());

        //verify all bidList
        mockMvc.perform(get("/api/curvePointList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(content().json(curvePointDtoListJson))
                .andExpect(status().isOk());

        //update curvePoint
        mockMvc.perform(put("/api/curvePoint")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("curvePointId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(curvePointDtoUpdateJson))
                .andExpect(content().json(curvePointDtoUpdateJson))
                .andExpect(status().isOk());

        //verify update curvePoint with findById
        mockMvc.perform(get("/api/curvePoint")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("curvePointId", "1"))
                .andExpect(content().json(curvePointDtoUpdateJson))
                .andExpect(status().isOk());

        //delete curvePoint
        mockMvc.perform(delete("/api/curvePoint")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("curvePointId", "1"))
                .andExpect(status().isOk());

        //verify delete curvePoint with findById
        mockMvc.perform(get("/api/curvePoint")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("curvePointId", "1"))
                .andExpect(status().isNotFound());


    }
}
