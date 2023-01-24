package com.nnk.poseidoninc.IT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Model.Dto.RatingDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RatingIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TokenService tokenService;

    RatingDto ratingDto1 = new RatingDto();
    RatingDto ratingDto1NoId = new RatingDto();

    RatingDto ratingDto2 = new RatingDto();
    RatingDto ratingDto2NoId = new RatingDto();

    RatingDto ratingDtoUpdate = new RatingDto();
    List<RatingDto> ratingDtoList = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();
    String ratingDto1Json;
    String ratingDto1NoIdJson;
    String ratingDto2Json;
    String ratingDto2NoIdJson;
    String ratingDtoUpdateJson;
    String ratingDtoListJson;
    String token;

    @BeforeAll
    void buildTest() throws JsonProcessingException {


        ratingDto1.setRatingId(1);
        ratingDto1.setMoodysRating("moodysTest1");
        ratingDto1.setSandPRating("SandPRTest1");
        ratingDto1.setFitchRating("fitchTest1");
        ratingDto1.setOrderNumber(1);

        ratingDto1NoId.setMoodysRating("moodysTest1");
        ratingDto1NoId.setSandPRating("SandPRTest1");
        ratingDto1NoId.setFitchRating("fitchTest1");
        ratingDto1NoId.setOrderNumber(1);


        ratingDto2.setRatingId(2);
        ratingDto2.setMoodysRating("moodysTest2");
        ratingDto2.setSandPRating("SandPRTest2");
        ratingDto2.setFitchRating("fitchTest2");
        ratingDto2.setOrderNumber(2);

        ratingDto2NoId.setMoodysRating("moodysTest2");
        ratingDto2NoId.setSandPRating("SandPRTest2");
        ratingDto2NoId.setFitchRating("fitchTest2");
        ratingDto2NoId.setOrderNumber(2);

        ratingDtoList.add(ratingDto1);
        ratingDtoList.add(ratingDto2);

        ratingDtoUpdate.setRatingId(1);
        ratingDtoUpdate.setMoodysRating("moodysTestUpdate");
        ratingDtoUpdate.setSandPRating("SandPRTestUpdate");
        ratingDtoUpdate.setFitchRating("fitchTestUpdate");
        ratingDtoUpdate.setOrderNumber(3);

        ratingDto1Json = objectMapper.writeValueAsString(ratingDto1);
        ratingDto1NoIdJson = objectMapper.writeValueAsString(ratingDto1NoId);

        ratingDto2Json = objectMapper.writeValueAsString(ratingDto2);
        ratingDto2NoIdJson = objectMapper.writeValueAsString(ratingDto2NoId);

        ratingDtoUpdateJson = objectMapper.writeValueAsString(ratingDtoUpdate);
        ratingDtoListJson = objectMapper.writeValueAsString(ratingDtoList);

        token = tokenService.generateToken(new UsernamePasswordAuthenticationToken("test", "Password1234!"));

    }

    @Test
    public void ratingIT() throws Exception {

        //add rating
        mockMvc.perform(post("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingDto1NoIdJson))
                .andExpect(content().json(ratingDto1Json))
                .andExpect(status().isOk());

        //findById rating
        mockMvc.perform(get("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "1"))
                .andExpect(content().json(ratingDto1Json))
                .andExpect(status().isOk());

        //add new rating
        mockMvc.perform(post("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingDto2NoIdJson))
                .andExpect(content().json(ratingDto2Json))
                .andExpect(status().isOk());

        //verify all rating
        mockMvc.perform(get("/api/ratingList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(content().json(ratingDtoListJson))
                .andExpect(status().isOk());

        //update rating
        mockMvc.perform(put("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingDtoUpdateJson))
                .andExpect(content().json(ratingDtoUpdateJson))
                .andExpect(status().isOk());

        //verify update with findById
        mockMvc.perform(get("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "1"))
                .andExpect(content().json(ratingDtoUpdateJson))
                .andExpect(status().isOk());

        //delete rating
        mockMvc.perform(delete("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "1"))
                .andExpect(status().isOk());

        //verify delete with findById
        mockMvc.perform(get("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "1"))
                .andExpect(status().isNotFound());


    }

}
