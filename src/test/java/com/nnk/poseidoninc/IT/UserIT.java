package com.nnk.poseidoninc.IT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Model.Dto.UserDto;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "userTEst", authorities = {"USER"})
public class UserIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TokenService tokenService;

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
    String token;

    @BeforeAll
    void buildTest() throws JsonProcessingException {

        objectMapper.disable(MapperFeature.USE_ANNOTATIONS);

        userDto1.setUserId(1);
        userDto1.setUserName("user");
        userDto1.setPassword("Password1234!");
        userDto1.setFullName("fullnameTest1");
        userDto1.setRole("roleTest1");

        userDto1NoId.setUserName("user");
        userDto1NoId.setPassword("Password1234!");
        userDto1NoId.setFullName("fullnameTest1");
        userDto1NoId.setRole("roleTest1");


        userDto2.setUserId(2);
        userDto2.setUserName("user2");
        userDto2.setPassword("Password1234!");
        userDto2.setFullName("fullnameTest2");
        userDto2.setRole("roleTest2");

        userDto2NoId.setUserName("user2");
        userDto2NoId.setPassword("Password1234!");
        userDto2NoId.setFullName("fullnameTest2");
        userDto2NoId.setRole("roleTest2");

        userDtoList.add(userDto1);
        userDtoList.add(userDto2);

        userDtoUpdate.setUserId(1);
        userDtoUpdate.setUserName("userUpdate");
        userDtoUpdate.setPassword("Password1234!");
        userDtoUpdate.setFullName("fullnameTestUpdate");
        userDtoUpdate.setRole("ADMIN");

        userDto1Json = objectMapper.writeValueAsString(userDto1);
        userDto1NoIdJson = objectMapper.writeValueAsString(userDto1NoId);

        userDto2Json = objectMapper.writeValueAsString(userDto2);
        userDto2NoIdJson = objectMapper.writeValueAsString(userDto2NoId);

        userDtoUpdateJson = objectMapper.writeValueAsString(userDtoUpdate);
        userDtoListJson = objectMapper.writeValueAsString(userDtoList);

        Collection<GrantedAuthority> scope = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
        scope.add(authority);

        token = tokenService.generateToken(new UsernamePasswordAuthenticationToken("user", "Password1234!",scope));

    }

    @Test
    public void userIt() throws Exception {

        //create user
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(userDto1NoIdJson))

                .andExpect(status().isOk());

        //findById
        mockMvc.perform(get("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "1"))
                .andExpect(status().isOk());

        //add user
        mockMvc.perform(post("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDto2NoIdJson))

                .andExpect(status().isOk());

        //verify findAll
        mockMvc.perform(get("/api/userList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))

                .andExpect(status().isOk());

        //update
        mockMvc.perform(put("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoUpdateJson))
                .andExpect(status().isOk());

        //verify update with findById
        mockMvc.perform(get("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "1"))

                .andExpect(status().isOk());

        //delete
        mockMvc.perform(delete("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "1"))
                .andExpect(status().isOk());

        //verify delete with findById
        mockMvc.perform(get("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "1"))
                .andExpect(status().isNotFound());
    }
}
