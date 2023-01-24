package com.nnk.poseidoninc.UT.ControllerWebApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Controller.ControllerWebApp.CurvePointControllerWebApp;
import com.nnk.poseidoninc.Model.CurvePoint;
import com.nnk.poseidoninc.Model.Dto.CurvePointDto;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Service.Implementation.CurvePointServiceImpl;
import com.nnk.poseidoninc.Service.Implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "user", authorities = {"Admin"})
class CurvePointControllerWebAppTest {

    @InjectMocks
    CurvePointControllerWebApp curvePointControllerWebApp;

    @MockBean
    UserServiceImpl userServiceMock;

    @MockBean
    CurvePointServiceImpl curvePointServiceMock;

    @Mock
    CurvePointServiceImpl curvePointService;

    @Mock
    Model model;

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

    UserDto userDto1 = new UserDto();

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

        userDto1.setUserId(1);
        userDto1.setUserName("user");
        userDto1.setPassword("Password1234!");
        userDto1.setFullName("fullnameTest1");
        userDto1.setRole("roleTest1");
    }

    @Test
    void home() throws Exception {

        when(curvePointServiceMock.findAll()).thenReturn(curvePointDtoList);
        when(userServiceMock.getCurrentUser(any())).thenReturn(userDto1);

        mockMvc.perform(get("/CurvePoint"))
                .andExpect(model().attribute("curvePointDtoList", curvePointDtoList))
                .andExpect(model().attribute("user", userDto1))
                .andExpect(status().isOk());

    }

    @Test
    void addCurvePointPage() throws Exception {
        mockMvc.perform(get("/CurvePoint/add"))
                .andExpect(view().name("curvePoint/add"))
                .andExpect(status().isOk());
    }

    @Test
    void addCurvePoint() throws Exception {
        when(curvePointServiceMock.create(any())).thenReturn(curvePointDto1);

        mockMvc.perform(post("/CurvePoint/add").with(csrf())
                        .param("term", "7")
                        .param("value", "7"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updatePage() {
        when(curvePointService.findById(anyInt())).thenReturn(curvePointDto1);

        model.addAttribute("curvePointDto", curvePointDto1);

        String test = curvePointControllerWebApp.updatePage(1, model);

        assertEquals("curvePoint/update", test);
    }

    @Test
    void update() throws Exception {
        when(curvePointServiceMock.update(any(), anyInt())).thenReturn(curvePointDtoUpdate);

        mockMvc.perform(post("/CurvePoint/update/1").with(csrf())
                        .param("term", "7")
                        .param("value", "7"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(curvePointServiceMock).delete(1);

        mockMvc.perform(get("/CurvePoint/delete/1").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}