package com.nnk.poseidoninc.UT.ControllerAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Controller.ControllerAPI.CurvePointControllerAPI;
import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.CurvePoint;
import com.nnk.poseidoninc.Model.Dto.CurvePointDto;
import com.nnk.poseidoninc.Service.Implementation.CurvePointServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
class CurvePointControllerAPITest {

    @InjectMocks
    CurvePointControllerAPI curvePointControllerAPI;

    @MockBean
    CurvePointServiceImpl curvePointServiceMock;

    @Autowired
    MockMvc mockMvc;


    CurvePointDto curvePointDto1 = new CurvePointDto();
    CurvePointDto curvePointDto2 = new CurvePointDto();
    CurvePointDto curvePointDtoUpdate = new CurvePointDto();

    CurvePoint curvePoint1 = new CurvePoint();
    CurvePoint curvePoint2 = new CurvePoint();
    CurvePoint curvePointUpdate = new CurvePoint();

    Optional<CurvePoint> curvePointOptional0 = Optional.empty();
    Optional<CurvePoint> curvePointOptional1;
    Optional<CurvePoint> curvePointOptional2;
    List<CurvePoint> curvePointListIterable = new ArrayList<>();
    List<CurvePointDto> curvePointDtoList = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();
    String curvePointDto1Json;
    String curvePointDtoListJson;

    @BeforeAll
    void buildTest() throws JsonProcessingException {

        curvePoint1.setCurvePointId(1);
        curvePoint1.setTerm(11);
        curvePoint1.setValue(11);
        curvePointOptional1 = Optional.of(curvePoint1);

        curvePoint2.setCurvePointId(2);
        curvePoint2.setTerm(22);
        curvePoint2.setValue(22);
        curvePointOptional2 = Optional.of(curvePoint2);

        curvePointListIterable.add(curvePoint1);
        curvePointListIterable.add(curvePoint2);

        curvePointDto1.setCurvePointId(1);
        curvePointDto1.setTerm(11);
        curvePointDto1.setValue(11);

        curvePointDto2.setCurvePointId(2);
        curvePointDto2.setTerm(22);
        curvePointDto2.setValue(22);

        curvePointDtoList.add(curvePointDto1);
        curvePointDtoList.add(curvePointDto2);

        curvePointUpdate.setCurvePointId(1);
        curvePointUpdate.setTerm(33);
        curvePointUpdate.setValue(33);

        curvePointDtoUpdate.setCurvePointId(1);
        curvePointDtoUpdate.setTerm(33);
        curvePointDtoUpdate.setValue(33);

        curvePointDto1Json = objectMapper.writeValueAsString(curvePointDto1);
        curvePointDtoListJson = objectMapper.writeValueAsString(curvePointDtoList);
    }

    @Test
    void findAll() throws Exception {
        when(curvePointServiceMock.findAll()).thenReturn(curvePointDtoList);

        mockMvc.perform(get("/curvePointList"))
                .andExpect(content().json(curvePointDtoListJson))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        when(curvePointServiceMock.create(curvePointDto1)).thenReturn(curvePointDto1);

        mockMvc.perform(post("/curvePoint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(curvePointDto1Json))
                .andExpect(content().json(curvePointDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void createBadRequest() throws Exception {
        when(curvePointServiceMock.create(curvePointDto1)).thenReturn(curvePointDto1);

        mockMvc.perform(post("/curvePoint"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findById() throws Exception {
        when(curvePointServiceMock.findById(1)).thenReturn(curvePointDto1);

        mockMvc.perform(get("/curvePoint")
                        .param("curvePointId", "1"))
                .andExpect(content().json(curvePointDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void findByIdBadRequest() throws Exception {

        mockMvc.perform(get("/curvePoint")
                        .param("curvePointId", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByIdNotFoundException() throws Exception {
        when(curvePointServiceMock.findById(1)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/curvePoint")
                        .param("curvePointId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        when(curvePointServiceMock.update(curvePointDto1, 1)).thenReturn(curvePointDto1);

        mockMvc.perform(put("/curvePoint")
                        .param("curvePointId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(curvePointDto1Json))
                .andExpect(content().json(curvePointDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void updateBadParam() throws Exception {
        when(curvePointServiceMock.update(curvePointDto1, 1)).thenReturn(curvePointDto1);

        mockMvc.perform(put("/curvePoint")
                        .param("curvePointId", "A")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(curvePointDto1Json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadBody() throws Exception {
        when(curvePointServiceMock.update(curvePointDto1, 1)).thenReturn(curvePointDto1);

        mockMvc.perform(put("/curvePoint")
                        .param("curvePointId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(curvePointDtoListJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateNotFoundException() throws Exception {
        when(curvePointServiceMock.update(curvePointDto1, 1)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/curvePoint")
                        .param("curvePointId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(curvePointDto1Json))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(curvePointServiceMock).delete(1);

        mockMvc.perform(delete("/curvePoint")
                .param("curvePointId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByIdBadRequest() throws Exception {
        doNothing().when(curvePointServiceMock).delete(1);

        mockMvc.perform(delete("/curvePoint")
                        .param("curvePointId", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteByIdNotFound() throws Exception {
        doThrow(NotFoundException.class).when(curvePointServiceMock).delete(1);

        mockMvc.perform(delete("/curvePoint")
                        .param("curvePointId", "1"))
                .andExpect(status().isNotFound());
    }
}