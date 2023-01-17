package com.nnk.poseidoninc.IT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
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
public class UserIT {

    @Autowired
    MockMvc mockMvc;

    UserDto userDto1 = new UserDto();
    UserDto userDto1NoId = new UserDto();
    UserDto userDto2 = new UserDto();
    UserDto userDto2NoId = new UserDto();
    UserDto userDtoUpdate = new UserDto();
    List<UserDto> userDtoList = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();
    String userDto1Json;
    String userDto1NoIdJson;
    String userDto2Json;
    String userDto2NoIdJson;
    String userDtoUpdateJson;
    String userDtoListJson;

    @BeforeAll
    void buildTest() throws JsonProcessingException {

        userDto1.setUserId(1);
        userDto1.setUserName("email@test1.com");
        userDto1.setPassword("passwordTest1");
        userDto1.setFullName("fullnameTest1");
        userDto1.setRole("roleTest1");

        userDto1NoId.setUserName("email@test1.com");
        userDto1NoId.setPassword("passwordTest1");
        userDto1NoId.setFullName("fullnameTest1");
        userDto1NoId.setRole("roleTest1");


        userDto2.setUserId(2);
        userDto2.setUserName("email@test2.com");
        userDto2.setPassword("passwordTest2");
        userDto2.setFullName("fullnameTest2");
        userDto2.setRole("roleTest2");

        userDto2NoId.setUserName("email@test2.com");
        userDto2NoId.setPassword("passwordTest2");
        userDto2NoId.setFullName("fullnameTest2");
        userDto2NoId.setRole("roleTest2");

        userDtoList.add(userDto1);
        userDtoList.add(userDto2);

        userDtoUpdate.setUserId(1);
        userDtoUpdate.setUserName("email@testUpdate.com");
        userDtoUpdate.setPassword("passwordTestUpdate");
        userDtoUpdate.setFullName("fullnameTestUpdate");
        userDtoUpdate.setRole("roleTestUpdate");

        userDto1Json = objectMapper.writeValueAsString(userDto1);
        userDto1NoIdJson = objectMapper.writeValueAsString(userDto1NoId);

        userDto2Json = objectMapper.writeValueAsString(userDto2);
        userDto2NoIdJson = objectMapper.writeValueAsString(userDto2NoId);

        userDtoUpdateJson = objectMapper.writeValueAsString(userDtoUpdate);
        userDtoListJson = objectMapper.writeValueAsString(userDtoList);
    }

    @Test
    public void userIt() throws Exception {

        //create user
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDto1NoIdJson))
                .andExpect(content().json(userDto1Json))
                .andExpect(status().isOk());

        //findById
        mockMvc.perform(get("/user")
                        .param("userId", "1"))
                .andExpect(content().json(userDto1Json))
                .andExpect(status().isOk());

        //add user
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDto2NoIdJson))
                .andExpect(content().json(userDto2Json))
                .andExpect(status().isOk());

        //verify findAll
        mockMvc.perform(get("/userList"))
                .andExpect(content().json(userDtoListJson))
                .andExpect(status().isOk());

        //update
        mockMvc.perform(put("/user")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoUpdateJson))
                .andExpect(content().json(userDtoUpdateJson))
                .andExpect(status().isOk());

        //verify update with findById
        mockMvc.perform(get("/user")
                        .param("userId", "1"))
                .andExpect(content().json(userDtoUpdateJson))
                .andExpect(status().isOk());

        //delete
        mockMvc.perform(delete("/user")
                        .param("userId", "1"))
                .andExpect(status().isOk());

        //verify delete with findById
        mockMvc.perform(get("/user")
                        .param("userId", "1"))
                .andExpect(status().isNotFound());
    }
}
