package com.nnk.poseidoninc.UT.ControllerWebApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Controller.ControllerWebApp.RuleNameControllerWebApp;
import com.nnk.poseidoninc.Model.Dto.RuleNameDto;
import com.nnk.poseidoninc.Model.Dto.UserDto;
import com.nnk.poseidoninc.Model.RuleName;
import com.nnk.poseidoninc.Service.Implementation.RuleNameServiceImpl;
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
import org.springframework.test.context.ActiveProfiles;
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
@WithMockUser(username = "user", authorities = {"USER"})
@ActiveProfiles("test")
class RuleNameControllerWebAppTest {

    @InjectMocks
    RuleNameControllerWebApp ruleNameControllerWebApp;

    @MockBean
    RuleNameServiceImpl ruleNameServiceMock;

    @MockBean
    UserServiceImpl userServiceMock;

    @Mock
    RuleNameServiceImpl ruleNameService;

    @Mock
    Model model;

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
        userDto1.setRole("roleTest1");

    }

    @Test
    void home() throws Exception {
        when(ruleNameServiceMock.findAll()).thenReturn(ruleNameDtoList);
        when(userServiceMock.getCurrentUser(any())).thenReturn(userDto1);

        mockMvc.perform(get("/RuleName"))
                .andExpect(model().attribute("ruleNameDtoList", ruleNameDtoList))
                .andExpect(status().isOk());
    }

    @Test
    void addRuleNamePage() throws Exception {
        mockMvc.perform(get("/RuleName/add"))
                .andExpect(view().name("ruleName/add"))
                .andExpect(status().isOk());
    }

    @Test
    void addRuleName() throws Exception {
        when(ruleNameServiceMock.create(any())).thenReturn(ruleNameDto1);

        mockMvc.perform(post("/RuleName/add").with(csrf())
                        .param("name", "nameTest")
                        .param("description", "descriptionTest")
                        .param("json", "jsonTest")
                        .param("template", "templateTest")
                        .param("sqlStr", "sqlStrTest")
                        .param("sqlPart", "sqlPartTest"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updateRuleNamePage() {
        when(ruleNameService.findById(anyInt())).thenReturn(ruleNameDto1);

        model.addAttribute("ruleNameDto", ruleNameDto1);

        String test = ruleNameControllerWebApp.updateRuleNamePage(1, model);

        assertEquals("ruleName/update", test);
    }

    @Test
    void updateRuleName() throws Exception {
        when(ruleNameServiceMock.update(any(), anyInt())).thenReturn(ruleNameDtoUpdate);

        mockMvc.perform(post("/RuleName/update/1").with(csrf())
                        .param("name", "nameTest")
                        .param("description", "descriptionTest")
                        .param("json", "jsonTest")
                        .param("template", "templateTest")
                        .param("sqlStr", "sqlStrTest")
                        .param("sqlPart", "sqlPartTest"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(ruleNameServiceMock).delete(1);

        mockMvc.perform(get("/RuleName/delete/1"))
                .andExpect(status().is3xxRedirection());
    }
}