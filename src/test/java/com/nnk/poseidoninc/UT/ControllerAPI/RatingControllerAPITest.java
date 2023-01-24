package com.nnk.poseidoninc.UT.ControllerAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Controller.ControllerAPI.RatingControllerAPI;
import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.RatingDto;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.Rating;
import com.nnk.poseidoninc.Security.TokenService;
import com.nnk.poseidoninc.Service.Implementation.RatingServiceImpl;
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
@WithMockUser(username = "userTEst", authorities = {"USER"})
class RatingControllerAPITest {

    @InjectMocks
    RatingControllerAPI ratingControllerAPI;

    @MockBean
    RatingServiceImpl ratingServiceMock;

    @MockBean
    UserServiceImpl userServiceMock;


    @Autowired
    MockMvc mockMvc;

    @Autowired
    TokenService tokenService;


    RatingDto ratingDto1 = new RatingDto();
    RatingDto ratingDto2 = new RatingDto();
    RatingDto ratingDtoUpdate = new RatingDto();
    Rating rating1 = new Rating();
    Rating rating2 = new Rating();
    Rating ratingUpdate = new Rating();

    Optional<Rating> ratingOptional0 = Optional.empty();
    Optional<Rating> ratingOptional1;
    Optional<Rating> ratingOptional2;

    List<Rating> ratingIterable = new ArrayList<>();
    List<RatingDto> ratingDtoList = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();
    String ratingDto1Json;
    String ratingDtoListJson;

    UserDto userDto1 = new UserDto();

    String token;

    @BeforeAll
    void buildTest() throws JsonProcessingException {

        rating1.setRatingId(1);
        rating1.setMoodysRating("moodysTest1");
        rating1.setSandPRating("SandPRTest1");
        rating1.setFitchRating("fitchTest1");
        rating1.setOrderNumber(1);
        ratingOptional1 = Optional.of(rating1);

        rating2.setRatingId(2);
        rating2.setMoodysRating("moodysTest2");
        rating2.setSandPRating("SandPRTest2");
        rating2.setFitchRating("fitchTest2");
        rating2.setOrderNumber(2);
        ratingOptional2 = Optional.of(rating2);

        ratingIterable.add(rating1);
        ratingIterable.add(rating2);

        ratingDto1.setRatingId(1);
        ratingDto1.setMoodysRating("moodysTest1");
        ratingDto1.setSandPRating("SandPRTest1");
        ratingDto1.setFitchRating("fitchTest1");
        ratingDto1.setOrderNumber(1);

        ratingDto2.setRatingId(2);
        ratingDto2.setMoodysRating("moodysTest2");
        ratingDto2.setSandPRating("SandPRTest2");
        ratingDto2.setFitchRating("fitchTest2");
        ratingDto2.setOrderNumber(2);

        ratingDtoList.add(ratingDto1);
        ratingDtoList.add(ratingDto2);

        ratingUpdate.setRatingId(1);
        ratingUpdate.setMoodysRating("moodysTestUpdate");
        ratingUpdate.setSandPRating("SandPRTestUpdate");
        ratingUpdate.setFitchRating("fitchTestUpdate");
        ratingUpdate.setOrderNumber(3);

        ratingDtoUpdate.setRatingId(1);
        ratingDtoUpdate.setMoodysRating("moodysTestUpdate");
        ratingDtoUpdate.setSandPRating("SandPRTestUpdate");
        ratingDtoUpdate.setFitchRating("fitchTestUpdate");
        ratingDtoUpdate.setOrderNumber(3);

        ratingDto1Json = objectMapper.writeValueAsString(ratingDto1);
        ratingDtoListJson = objectMapper.writeValueAsString(ratingDtoList);

        userDto1.setUserId(1);
        userDto1.setUserName("user");
        userDto1.setPassword("Password1234!");
        userDto1.setFullName("fullnameTest1");
        userDto1.setRole("ADMIN");

        token = tokenService.generateToken(new UsernamePasswordAuthenticationToken("test", "Password1234!"));

    }


    @Test
    void findAll() throws Exception {
        when(ratingServiceMock.findAll()).thenReturn(ratingDtoList);
        when(userServiceMock.getCurrentUser(any())).thenReturn(userDto1);

        mockMvc.perform(get("/api/ratingList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(content().json(ratingDtoListJson))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        when(ratingServiceMock.create(ratingDto1)).thenReturn(ratingDto1);

        mockMvc.perform(post("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingDto1Json))
                .andExpect(content().json(ratingDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void createBadRequest() throws Exception {
        when(ratingServiceMock.create(ratingDto1)).thenReturn(ratingDto1);

        mockMvc.perform(post("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingDtoListJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findById() throws Exception {
        when(ratingServiceMock.findById(1)).thenReturn(ratingDto1);

        mockMvc.perform(get("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "1"))
                .andExpect(content().json(ratingDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void findByIdBadRequest() throws Exception {
        when(ratingServiceMock.findById(1)).thenReturn(ratingDto1);

        mockMvc.perform(get("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByIdNotFoundException() throws Exception {
        when(ratingServiceMock.findById(1)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        when(ratingServiceMock.update(ratingDto1, 1)).thenReturn(ratingDto1);

        mockMvc.perform(put("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingDto1Json))
                .andExpect(content().json(ratingDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void updateBadParam() throws Exception {
        when(ratingServiceMock.update(ratingDto1, 1)).thenReturn(ratingDto1);

        mockMvc.perform(put("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "A")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingDto1Json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadBody() throws Exception {
        when(ratingServiceMock.update(ratingDto1, 1)).thenReturn(ratingDto1);

        mockMvc.perform(put("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingDtoListJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateNotFoundException() throws Exception {
        when(ratingServiceMock.update(ratingDto1, 1)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingDto1Json))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(ratingServiceMock).delete(1);

        mockMvc.perform(delete("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByIdBadRequest() throws Exception {
        doNothing().when(ratingServiceMock).delete(1);

        mockMvc.perform(delete("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteByIdNotFoundException() throws Exception {
        doThrow(NotFoundException.class).when(ratingServiceMock).delete(1);

        mockMvc.perform(delete("/api/rating")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ratingId", "1"))
                .andExpect(status().isNotFound());
    }

}