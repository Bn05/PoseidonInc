package com.nnk.poseidoninc.IT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Model.Dto.RuleNameDto;
import com.nnk.poseidoninc.Model.RuleName;
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
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RuleNameIT {

    @Autowired
    MockMvc mockMvc;

    RuleNameDto ruleNameDto1 = new RuleNameDto();
    RuleNameDto ruleNameDto1NoId = new RuleNameDto();

    RuleNameDto ruleNameDto2 = new RuleNameDto();
    RuleNameDto ruleNameDto2NoId = new RuleNameDto();

    RuleNameDto ruleNameDtoUpdate = new RuleNameDto();
    List<RuleNameDto> ruleNameDtoList = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();
    String ruleNameDto1Json;
    String ruleNameDto1NoIdJson;
    String ruleNameDto2Json;
    String ruleNameDto2NoIdJson;
    String ruleNameDtoUpdateJson;
    String ruleNameDtoListJson;

    @BeforeAll
    void buildTest() throws JsonProcessingException {


        ruleNameDto1.setRuleNameId(1);
        ruleNameDto1.setName("nameTest1");
        ruleNameDto1.setDescription("descriptionTest1");
        ruleNameDto1.setJson("jsonTest1");
        ruleNameDto1.setTemplate("templateTest1");
        ruleNameDto1.setSqlStr("sqlStrTest1");
        ruleNameDto1.setSqlPart("sqlPart1");

        ruleNameDto1NoId.setName("nameTest1");
        ruleNameDto1NoId.setDescription("descriptionTest1");
        ruleNameDto1NoId.setJson("jsonTest1");
        ruleNameDto1NoId.setTemplate("templateTest1");
        ruleNameDto1NoId.setSqlStr("sqlStrTest1");
        ruleNameDto1NoId.setSqlPart("sqlPart1");

        ruleNameDto2.setRuleNameId(2);
        ruleNameDto2.setName("nameTest2");
        ruleNameDto2.setDescription("descriptionTest2");
        ruleNameDto2.setJson("jsonTest2");
        ruleNameDto2.setTemplate("templateTest2");
        ruleNameDto2.setSqlStr("sqlStrTest2");
        ruleNameDto2.setSqlPart("sqlPart2");

        ruleNameDto2NoId.setName("nameTest2");
        ruleNameDto2NoId.setDescription("descriptionTest2");
        ruleNameDto2NoId.setJson("jsonTest2");
        ruleNameDto2NoId.setTemplate("templateTest2");
        ruleNameDto2NoId.setSqlStr("sqlStrTest2");
        ruleNameDto2NoId.setSqlPart("sqlPart2");

        ruleNameDtoList.add(ruleNameDto1);
        ruleNameDtoList.add(ruleNameDto2);


        ruleNameDtoUpdate.setRuleNameId(1);
        ruleNameDtoUpdate.setName("nameTestUpdate");
        ruleNameDtoUpdate.setDescription("descriptionTestUpdate");
        ruleNameDtoUpdate.setJson("jsonTestUpdate");
        ruleNameDtoUpdate.setTemplate("templateTestUpdate");
        ruleNameDtoUpdate.setSqlStr("sqlStrTestUpdate");
        ruleNameDtoUpdate.setSqlPart("sqlPartUpdate");

        ruleNameDto1Json = objectMapper.writeValueAsString(ruleNameDto1);
        ruleNameDto1NoIdJson = objectMapper.writeValueAsString(ruleNameDto1NoId);
        ruleNameDto2Json = objectMapper.writeValueAsString(ruleNameDto2);
        ruleNameDto2NoIdJson = objectMapper.writeValueAsString(ruleNameDto2NoId);
        ruleNameDtoUpdateJson = objectMapper.writeValueAsString(ruleNameDtoUpdate);
        ruleNameDtoListJson = objectMapper.writeValueAsString(ruleNameDtoList);

    }

    @Test
    public void ruleNameIT() throws Exception {

        //create ruleName
        mockMvc.perform(post("/ruleName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleNameDto1NoIdJson))
                .andExpect(content().json(ruleNameDto1Json))
                .andExpect(status().isOk());

        //findById
        mockMvc.perform(get("/ruleName")
                        .param("ruleNameId", "1"))
                .andExpect(content().json(ruleNameDto1Json))
                .andExpect(status().isOk());

        // add new ruleName
        mockMvc.perform(post("/ruleName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleNameDto2NoIdJson))
                .andExpect(content().json(ruleNameDto2Json))
                .andExpect(status().isOk());

        //verify all ruleName
        mockMvc.perform(get("/ruleNameList"))
                .andExpect(content().json(ruleNameDtoListJson))
                .andExpect(status().isOk());

        //update ruleName
        mockMvc.perform(put("/ruleName")
                        .param("ruleNameId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleNameDtoUpdateJson))
                .andExpect(content().json(ruleNameDtoUpdateJson))
                .andExpect(status().isOk());

        //verify update with findById
        mockMvc.perform(get("/ruleName")
                        .param("ruleNameId", "1"))
                .andExpect(content().json(ruleNameDtoUpdateJson))
                .andExpect(status().isOk());

        //delete
        mockMvc.perform(delete("/ruleName")
                        .param("ruleNameId", "1"))
                .andExpect(status().isOk());

        //verify delete with findById
        mockMvc.perform(get("/ruleName")
                        .param("ruleNameId", "1"))
                .andExpect(status().isNotFound());


    }


}
