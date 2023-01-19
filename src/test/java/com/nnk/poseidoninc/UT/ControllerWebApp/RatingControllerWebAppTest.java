package com.nnk.poseidoninc.UT.ControllerWebApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Controller.ControllerWebApp.RatingControllerWebApp;
import com.nnk.poseidoninc.Model.Dto.RatingDto;
import com.nnk.poseidoninc.Model.Rating;
import com.nnk.poseidoninc.Service.Implementation.RatingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "userTEst", authorities = {"USER"})
class RatingControllerWebAppTest {

    @InjectMocks
    RatingControllerWebApp ratingControllerWebApp;

    @MockBean
    RatingServiceImpl ratingServiceMock;

    @Mock
    RatingServiceImpl ratingService;

    @Mock
    Model model;

    @Autowired
    MockMvc mockMvc;

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

    @Test
    void home() throws Exception {
        when(ratingServiceMock.findAll()).thenReturn(ratingDtoList);

        mockMvc.perform(get("/Rating"))
                .andExpect(model().attribute("user", "USER TESTA"))
                .andExpect(model().attribute("ratingDtoList", ratingDtoList))
                .andExpect(status().isOk());
    }

    @Test
    void addRatingPage() throws Exception {
        mockMvc.perform(get("/Rating/add"))
                .andExpect(view().name("rating/add"))
                .andExpect(status().isOk());
    }

    @Test
    void addRating() throws Exception {
        when(ratingServiceMock.create(any())).thenReturn(ratingDto1);

        mockMvc.perform(post("/Rating/add")
                        .param("moodysRating", "RequestTest")
                        .param("sandPRating", "RequestTest")
                        .param("fitchRating", "RequestTest")
                        .param("orderNumber", "7"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updateRatingPage() {
        when(ratingService.findById(anyInt())).thenReturn(ratingDto1);

        model.addAttribute("ratingDto", ratingDto1);

        String test = ratingControllerWebApp.updateRatingPage(1, model);

        assertEquals("rating/update", test);
    }

    @Test
    void updateRating() throws Exception {
        when(ratingServiceMock.update(any(), anyInt())).thenReturn(ratingDtoUpdate);

        mockMvc.perform(post("/Rating/update/1")
                        .param("moodysRating", "RequestTest")
                        .param("sandPRating", "RequestTest")
                        .param("fitchRating", "RequestTest")
                        .param("orderNumber", "7"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(ratingServiceMock).delete(1);

        mockMvc.perform(get("/Rating/delete/1"))
                .andExpect(status().is3xxRedirection());
    }
}