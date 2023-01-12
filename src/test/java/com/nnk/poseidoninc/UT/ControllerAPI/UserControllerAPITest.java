package com.nnk.poseidoninc.UT.ControllerAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.ControllerAPI.UserControllerAPI;
import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.User;
import com.nnk.poseidoninc.Repository.UserRepository;
import com.nnk.poseidoninc.Service.Implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerAPITest {

    @InjectMocks
    UserControllerAPI userControllerAPI;

    @MockBean
    UserServiceImpl userServiceMock;

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
        user1.setEmail("email@test1.com");
        user1.setPassword("passwordTest1");
        user1.setFullName("fullnameTest1");
        user1.setRole("roleTest1");
        userOptional1 = Optional.of(user1);

        user2.setUserId(2);
        user2.setEmail("email@test2.com");
        user2.setPassword("passwordTest2");
        user2.setFullName("fullnameTest2");
        user2.setRole("roleTest2");
        userOptional2 = Optional.of(user2);

        userIterable.add(user1);
        userIterable.add(user2);


        userDto1.setUserId(1);
        userDto1.setEmail("email@test1.com");
        userDto1.setPassword("passwordTest1");
        userDto1.setFullName("fullnameTest1");
        userDto1.setRole("roleTest1");


        userDto2.setUserId(2);
        userDto2.setEmail("email@test2.com");
        userDto2.setPassword("passwordTest2");
        userDto2.setFullName("fullnameTest2");
        userDto2.setRole("roleTest2");

        userDtoList.add(userDto1);
        userDtoList.add(userDto2);


        userUpdate.setUserId(1);
        userUpdate.setEmail("email@testUpdate.com");
        userUpdate.setPassword("passwordTestUpdate");
        userUpdate.setFullName("fullnameTestUpdate");
        userUpdate.setRole("roleTestUpdate");

        userDtoUpdate.setUserId(1);
        userDtoUpdate.setEmail("email@testUpdate.com");
        userDtoUpdate.setPassword("passwordTestUpdate");
        userDtoUpdate.setFullName("fullnameTestUpdate");
        userDtoUpdate.setRole("roleTestUpdate");

        userDto1Json = objectMapper.writeValueAsString(userDto1);
        userDtoListJson = objectMapper.writeValueAsString(userDtoList);
    }

    @Test
    void findAll() throws Exception {
        when(userServiceMock.findAll()).thenReturn(userDtoList);

        mockMvc.perform(get("/userList"))
                .andExpect(content().json(userDtoListJson))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        when(userServiceMock.create(userDto1)).thenReturn(userDto1);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDto1Json))
                .andExpect(content().json(userDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void createBadBody() throws Exception {
        when(userServiceMock.create(userDto1)).thenReturn(userDto1);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoListJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findById() throws Exception {
        when(userServiceMock.findById(1)).thenReturn(userDto1);

        mockMvc.perform(get("/user")
                        .param("userId", "1"))
                .andExpect(content().json(userDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void findByIdBadParam() throws Exception {

        mockMvc.perform(get("/user")
                        .param("userId", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByIdNotFoundException() throws Exception {
        when(userServiceMock.findById(1)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/user")
                        .param("userId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        when(userServiceMock.update(userDto1, 1)).thenReturn(userDto1);

        mockMvc.perform(put("/user")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDto1Json))
                .andExpect(content().json(userDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void updateBadParam() throws Exception {

        mockMvc.perform(put("/user")
                        .param("userId", "A")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDto1Json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadBody() throws Exception {
        when(userServiceMock.update(userDto1, 1)).thenReturn(userDto1);

        mockMvc.perform(put("/user")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoListJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateNotFoundException() throws Exception {
        when(userServiceMock.update(userDto1, 1)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/user")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDto1Json))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(userServiceMock).delete(1);

        mockMvc.perform(delete("/user")
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByIdBadParam() throws Exception {
        doNothing().when(userServiceMock).delete(1);

        mockMvc.perform(delete("/user")
                        .param("userId", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteByIdNotFoundException() throws Exception {
        doThrow(NotFoundException.class).when(userServiceMock).delete(1);

        mockMvc.perform(delete("/user")
                        .param("userId", "1"))
                .andExpect(status().isNotFound());
    }
}