package com.nnk.poseidoninc.UT.ControllerAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Controller.ControllerAPI.UserControllerAPI;
import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.User;
import com.nnk.poseidoninc.Security.TokenService;
import com.nnk.poseidoninc.Service.Implementation.BidListServiceImpl;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    @MockBean
    BidListServiceImpl bidListServiceMock;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TokenService tokenService;

    User user1 = new User();
    User user2 = new User();
    User userUpdate = new User();
    UserDto userDto1 = new UserDto();
    UserDto userDto2 = new UserDto();

    UserDto userDto1NoPassword = new UserDto();
    UserDto userDtoUpdate = new UserDto();

    Optional<User> userOptional0 = Optional.empty();
    Optional<User> userOptional1;
    Optional<User> userOptional2;
    List<User> userIterable = new ArrayList<>();
    List<UserDto> userDtoList = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();
    String userDto1Json;

    String userJson;
    String userDtoListJson;

    String userDto1NoPasswordJson;

    String token;

    @BeforeAll
    void buildTest() throws JsonProcessingException {

        objectMapper.disable(MapperFeature.USE_ANNOTATIONS);

        user1.setUserId(1);
        user1.setUserName("user");
        user1.setPassword("Password1234!");
        user1.setFullName("fullnameTest1");
        user1.setRole("ADMIN");
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
        userDto1.setUserName("userTEST");
        userDto1.setPassword("Password1234!");
        userDto1.setFullName("fullname");
        userDto1.setRole("ADMIN");

        userDto1NoPassword.setUserId(1);
        userDto1NoPassword.setUserName("userTEST");
        userDto1NoPassword.setFullName("fullname");
        userDto1NoPassword.setRole("ADMIN");


        userDto2.setUserId(2);
        userDto2.setUserName("email@test2.com");
        userDto2.setPassword("passwordTest2");
        userDto2.setFullName("fullnameTest2");
        userDto2.setRole("roleTest2");

        userDtoList.add(userDto1);
        userDtoList.add(userDto2);


        userUpdate.setUserId(1);
        userUpdate.setUserName("user");
        userUpdate.setPassword("Password1234!");
        userUpdate.setFullName("fullnameTestUpdate");
        userUpdate.setRole("ADMIN");

        userDtoUpdate.setUserId(1);
        userDtoUpdate.setUserName("user");
        userDtoUpdate.setPassword("Password1234!");
        userDtoUpdate.setFullName("fullnameTestUpdate");
        userDtoUpdate.setRole("ADMIN");

        userDto1Json = objectMapper.writeValueAsString(userDto1);
        userDto1NoPasswordJson = objectMapper.writeValueAsString(userDto1NoPassword);
        userDtoListJson = objectMapper.writeValueAsString(userDtoList);


        Collection<GrantedAuthority> scope = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
        scope.add(authority);

        token = tokenService.generateToken(new UsernamePasswordAuthenticationToken("test", "Password1234!", scope));

    }

    @Test
    void findAll() throws Exception {
        when(userServiceMock.findAll()).thenReturn(userDtoList);
        when(userServiceMock.getCurrentUser(any())).thenReturn(userDto1);

        mockMvc.perform(get("/api/userList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        when(userServiceMock.create(userDto1)).thenReturn(userDto1);

        mockMvc.perform(post("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void createBadBody() throws Exception {
        when(userServiceMock.create(userDto1)).thenReturn(userDto1);

        mockMvc.perform(post("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoListJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findById() throws Exception {
        when(userServiceMock.findById(1)).thenReturn(userDto1);

        mockMvc.perform(get("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void findByIdBadParam() throws Exception {

        mockMvc.perform(get("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByIdNotFoundException() throws Exception {
        when(userServiceMock.findById(1)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        when(userServiceMock.update(userDto1, 1)).thenReturn(userDto1);

        mockMvc.perform(put("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void updateBadParam() throws Exception {

        mockMvc.perform(put("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "A")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDto1Json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadBody() throws Exception {
        when(userServiceMock.update(userDto1, 1)).thenReturn(userDto1);

        mockMvc.perform(put("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoListJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateNotFoundException() throws Exception {
        when(userServiceMock.update(userDto1, 1)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDto1Json))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(userServiceMock).delete(1);

        mockMvc.perform(delete("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByIdBadParam() throws Exception {
        doNothing().when(userServiceMock).delete(1);

        mockMvc.perform(delete("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteByIdNotFoundException() throws Exception {
        doThrow(NotFoundException.class).when(userServiceMock).delete(1);

        mockMvc.perform(delete("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("userId", "1"))
                .andExpect(status().isNotFound());
    }
}