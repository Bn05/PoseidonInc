package com.nnk.poseidoninc.UT.ControllerAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Controller.ControllerAPI.RuleNameControllerAPI;
import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.RuleNameDto;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.RuleName;
import com.nnk.poseidoninc.Security.TokenService;
import com.nnk.poseidoninc.Service.Implementation.RuleNameServiceImpl;
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
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
class RuleNameControllerAPITest {

    @InjectMocks
    RuleNameControllerAPI ruleNameControllerAPI;

    @MockBean
    RuleNameServiceImpl ruleNameServiceMock;

    @MockBean
    UserServiceImpl userServiceMock;

    @Autowired
    TokenService tokenService;

    @Autowired
    MockMvc mockMvc;

    RuleNameDto ruleNameDto1 = new RuleNameDto();
    RuleNameDto ruleNameDto2 = new RuleNameDto();
    RuleNameDto ruleNameDtoUpdate = new RuleNameDto();
    RuleName ruleName1 = new RuleName();
    RuleName ruleName2 = new RuleName();
    RuleName ruleNameUpdate = new RuleName();

    Optional<RuleName> ruleNameOptional0 = Optional.empty();
    Optional<RuleName> ruleNameOptional1;
    Optional<RuleName> ruleNameOptional2;
    List<RuleName> ruleNameIterable = new ArrayList<>();
    List<RuleNameDto> ruleNameDtoList = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();
    String ruleNameDto1Json;
    String ruleNameDtoListJson;

    UserDto userDto1 = new UserDto();

    String token;

    @BeforeAll
    void buildTest() throws JsonProcessingException {

        ruleName1.setRuleNameId(1);
        ruleName1.setName("nameTest1");
        ruleName1.setDescription("descriptionTest1");
        ruleName1.setJson("jsonTest1");
        ruleName1.setTemplate("templateTest1");
        ruleName1.setSqlStr("sqlStrTest1");
        ruleName1.setSqlPart("sqlPart1");
        ruleNameOptional1 = Optional.of(ruleName1);

        ruleName2.setRuleNameId(2);
        ruleName2.setName("nameTest2");
        ruleName2.setDescription("descriptionTest2");
        ruleName2.setJson("jsonTest2");
        ruleName2.setTemplate("templateTest2");
        ruleName2.setSqlStr("sqlStrTest2");
        ruleName2.setSqlPart("sqlPart2");
        ruleNameOptional2 = Optional.of(ruleName2);

        ruleNameIterable.add(ruleName1);
        ruleNameIterable.add(ruleName2);

        ruleNameDto1.setRuleNameId(1);
        ruleNameDto1.setName("nameTest1");
        ruleNameDto1.setDescription("descriptionTest1");
        ruleNameDto1.setJson("jsonTest1");
        ruleNameDto1.setTemplate("templateTest1");
        ruleNameDto1.setSqlStr("sqlStrTest1");
        ruleNameDto1.setSqlPart("sqlPart1");

        ruleNameDto2.setRuleNameId(2);
        ruleNameDto2.setName("nameTest2");
        ruleNameDto2.setDescription("descriptionTest2");
        ruleNameDto2.setJson("jsonTest2");
        ruleNameDto2.setTemplate("templateTest2");
        ruleNameDto2.setSqlStr("sqlStrTest2");
        ruleNameDto2.setSqlPart("sqlPart2");

        ruleNameDtoList.add(ruleNameDto1);
        ruleNameDtoList.add(ruleNameDto2);


        ruleNameUpdate.setRuleNameId(1);
        ruleNameUpdate.setName("nameTestUpdate");
        ruleNameUpdate.setDescription("descriptionTestUpdate");
        ruleNameUpdate.setJson("jsonTestUpdate");
        ruleNameUpdate.setTemplate("templateTestUpdate");
        ruleNameUpdate.setSqlStr("sqlStrTestUpdate");
        ruleNameUpdate.setSqlPart("sqlPartUpdate");

        ruleNameDtoUpdate.setRuleNameId(1);
        ruleNameDtoUpdate.setName("nameTestUpdate");
        ruleNameDtoUpdate.setDescription("descriptionTestUpdate");
        ruleNameDtoUpdate.setJson("jsonTestUpdate");
        ruleNameDtoUpdate.setTemplate("templateTestUpdate");
        ruleNameDtoUpdate.setSqlStr("sqlStrTestUpdate");
        ruleNameDtoUpdate.setSqlPart("sqlPartUpdate");

        ruleNameDto1Json = objectMapper.writeValueAsString(ruleNameDto1);
        ruleNameDtoListJson = objectMapper.writeValueAsString(ruleNameDtoList);

        userDto1.setUserId(1);
        userDto1.setUserName("user");
        userDto1.setPassword("Password1234!");
        userDto1.setFullName("fullnameTest1");
        userDto1.setRole("ADMIN");

        token = tokenService.generateToken(new UsernamePasswordAuthenticationToken("test", "Password1234!"));

    }

    @Test
    void findAll() throws Exception {
        when(ruleNameServiceMock.findAll()).thenReturn(ruleNameDtoList);
        when(userServiceMock.getCurrentUser(any())).thenReturn(userDto1);

        mockMvc.perform(get("/api/ruleNameList")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(content().json(ruleNameDtoListJson))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        when(ruleNameServiceMock.create(ruleNameDto1)).thenReturn(ruleNameDto1);

        mockMvc.perform(post("/api/ruleName")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleNameDto1Json))
                .andExpect(content().json(ruleNameDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void createBadRequest() throws Exception {
        when(ruleNameServiceMock.create(ruleNameDto1)).thenReturn(ruleNameDto1);

        mockMvc.perform(post("/api/ruleName")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleNameDtoListJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findById() throws Exception {
        when(ruleNameServiceMock.findById(1)).thenReturn(ruleNameDto1);

        mockMvc.perform(get("/api/ruleName")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ruleNameId", "1"))
                .andExpect(content().json(ruleNameDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void findByIdBadRequest() throws Exception {

        mockMvc.perform(get("/api/ruleName")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ruleNameId", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByIdNotFoundException() throws Exception {
        when(ruleNameServiceMock.findById(1)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/ruleName")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ruleNameId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        when(ruleNameServiceMock.update(ruleNameDto1, 1)).thenReturn(ruleNameDto1);

        mockMvc.perform(put("/api/ruleName")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ruleNameId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleNameDto1Json))
                .andExpect(content().json(ruleNameDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void updateBadParam() throws Exception {

        mockMvc.perform(put("/api/ruleName")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ruleNameId", "A")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleNameDto1Json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadBody() throws Exception {

        mockMvc.perform(put("/api/ruleName")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ruleNameId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleNameDtoListJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateNotFoundException() throws Exception {
        when(ruleNameServiceMock.update(ruleNameDto1, 1)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/ruleName")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ruleNameId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleNameDto1Json))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(ruleNameServiceMock).delete(1);

        mockMvc.perform(delete("/api/ruleName")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ruleNameId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByIdBadParam() throws Exception {
        doNothing().when(ruleNameServiceMock).delete(1);

        mockMvc.perform(delete("/api/ruleName")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ruleNameId", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteByIdNotFoundException() throws Exception {
        doThrow(NotFoundException.class).when(ruleNameServiceMock).delete(1);

        mockMvc.perform(delete("/api/ruleName")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("ruleNameId", "1"))
                .andExpect(status().isNotFound());
    }
}