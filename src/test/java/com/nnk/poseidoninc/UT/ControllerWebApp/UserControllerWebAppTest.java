package com.nnk.poseidoninc.UT.ControllerWebApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Controller.ControllerWebApp.UserControllerWebApp;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.User;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "userTEst", authorities = {"USER"})
class UserControllerWebAppTest {

    @InjectMocks
    UserControllerWebApp userControllerWebApp;

    @MockBean
    UserServiceImpl userServiceMock;

    @Mock
    UserServiceImpl userService;

    @Mock
    Model model;

    @Autowired
    MockMvc mockMvc;

    User user1 = new User();
    User user2 = new User();
    User userUpdate = new User();
    UserDto userDto1 = new UserDto();
    UserDto userDto2 = new UserDto();
    UserDto userDtoUpdate = new UserDto();

    Optional<User> userOptional0 = Optional.empty();
    Optional<User> userOptional1;
    Optional<User> userOptional2;
    List<User> userIterable = new ArrayList<>();
    List<UserDto> userDtoList = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();
    String userDto1Json;
    String userDtoListJson;

    @BeforeAll
    void buildTest() throws JsonProcessingException {

        user1.setUserId(1);
        user1.setUserName("email@test1.com");
        user1.setPassword("passwordTest1");
        user1.setFullName("fullnameTest1");
        user1.setRole("roleTest1");
        userOptional1 = Optional.of(user1);

        user2.setUserId(2);
        user2.setUserName("email@test2.com");
        user2.setPassword("passwordTest2");
        user2.setFullName("fullnameTest2");
        user2.setRole("roleTest2");
        userOptional2 = Optional.of(user2);

        userIterable.add(user1);
        userIterable.add(user2);


        userDto1.setUserId(1);
        userDto1.setUserName("email@test1.com");
        userDto1.setPassword("Password1234!");
        userDto1.setFullName("fullnameTest1");
        userDto1.setRole("roleTest1");


        userDto2.setUserId(2);
        userDto2.setUserName("email@test2.com");
        userDto2.setPassword("passwordTest2");
        userDto2.setFullName("fullnameTest2");
        userDto2.setRole("roleTest2");

        userDtoList.add(userDto1);
        userDtoList.add(userDto2);


        userUpdate.setUserId(1);
        userUpdate.setUserName("email@testUpdate.com");
        userUpdate.setPassword("passwordTestUpdate");
        userUpdate.setFullName("fullnameTestUpdate");
        userUpdate.setRole("roleTestUpdate");

        userDtoUpdate.setUserId(1);
        userDtoUpdate.setUserName("email@testUpdate.com");
        userDtoUpdate.setPassword("Password1234!");
        userDtoUpdate.setFullName("fullnameTestUpdate");
        userDtoUpdate.setRole("roleTestUpdate");

        userDto1Json = objectMapper.writeValueAsString(userDto1);
        userDtoListJson = objectMapper.writeValueAsString(userDtoList);
    }

    @Test
    void home() throws Exception {
        when(userServiceMock.findAll()).thenReturn(userDtoList);

        mockMvc.perform(get("/User"))
                .andExpect(model().attribute("userDtoList", userDtoList))
                .andExpect(status().isOk());
    }

    @Test
    void addUserPage() throws Exception {
        mockMvc.perform(get("/User/add"))
                .andExpect(view().name("user/add"))
                .andExpect(status().isOk());
    }

    @Test
    void addUser() throws Exception {
        when(userServiceMock.create(any())).thenReturn(userDto1);

        mockMvc.perform(post("/User/add")
                        .param("userName", "userName")
                        .param("password", "passwordTest1")
                        .param("fullName", "fullNameTest")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updateUserPage() {
        when(userService.findById(anyInt())).thenReturn(userDto1);

        model.addAttribute("userDto", userDto1);

        String test = userControllerWebApp.updateUserPage(1, model);

        assertEquals("user/update", test);
    }

    @Test
    void updateUser() throws Exception {
        when(userServiceMock.update(any(), anyInt())).thenReturn(userDtoUpdate);

        mockMvc.perform(post("/User/update/1")
                        .param("userName", "userName")
                        .param("password", "passwordTest1")
                        .param("fullName", "fullNameTest")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(userServiceMock).delete(1);

        mockMvc.perform(get("/User/delete/1"))
                .andExpect(status().is3xxRedirection());
    }
}