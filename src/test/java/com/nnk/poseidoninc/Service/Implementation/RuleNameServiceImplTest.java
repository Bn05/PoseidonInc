package com.nnk.poseidoninc.Service.Implementation;

import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.RuleNameDto;
import com.nnk.poseidoninc.Model.RuleName;
import com.nnk.poseidoninc.Repository.RuleNameRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RuleNameServiceImplTest {

    @InjectMocks
    RuleNameServiceImpl ruleNameService;

    @Mock
    RuleNameRepository ruleNameRepositoryMock;


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

    @BeforeAll
    void buildTest() {

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


        ruleNameUpdate.setRuleNameId(3);
        ruleNameUpdate.setName("nameTestUpdate");
        ruleNameUpdate.setDescription("descriptionTestUpdate");
        ruleNameUpdate.setJson("jsonTestUpdate");
        ruleNameUpdate.setTemplate("templateTestUpdate");
        ruleNameUpdate.setSqlStr("sqlStrTestUpdate");
        ruleNameUpdate.setSqlPart("sqlPartUpdate");

        ruleNameDtoUpdate.setRuleNameId(3);
        ruleNameDtoUpdate.setName("nameTestUpdate");
        ruleNameDtoUpdate.setDescription("descriptionTestUpdate");
        ruleNameDtoUpdate.setJson("jsonTestUpdate");
        ruleNameDtoUpdate.setTemplate("templateTestUpdate");
        ruleNameDtoUpdate.setSqlStr("sqlStrTestUpdate");
        ruleNameDtoUpdate.setSqlPart("sqlPartUpdate");


    }

    @Test
    void findAll() {
        when(ruleNameRepositoryMock.findAll()).thenReturn(ruleNameIterable);

        List<RuleNameDto> ruleNameDtoListResult = ruleNameService.findAll();

        verify(ruleNameRepositoryMock, times(1)).findAll();
        assertEquals(ruleNameDtoList, ruleNameDtoListResult);
    }

    @Test
    void create() {
        when(ruleNameRepositoryMock.save(any())).thenReturn(ruleName1);

        RuleNameDto ruleNameDtoResult = ruleNameService.create(ruleNameDto1);

        verify(ruleNameRepositoryMock, times(1)).save(any());
        assertEquals(ruleNameDto1, ruleNameDtoResult);
    }

    @Test
    void findByIdExist() {
        when(ruleNameRepositoryMock.findById(1)).thenReturn(ruleNameOptional1);

        RuleNameDto ruleNameDtoResult = ruleNameService.findById(1);

        verify(ruleNameRepositoryMock, times(1)).findById(1);
        assertEquals(ruleNameDto1, ruleNameDtoResult);
    }

    @Test
    void findByIdNoExist() {
        when(ruleNameRepositoryMock.findById(1)).thenReturn(ruleNameOptional0);
        boolean errorTest = true;

        try {
            RuleNameDto ruleNameDtoResult = ruleNameService.findById(1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(ruleNameRepositoryMock, times(1)).findById(1);
        assertFalse(errorTest);
    }

    @Test
    void updateIdExist() {
        when(ruleNameRepositoryMock.findById(1)).thenReturn(ruleNameOptional1);
        when(ruleNameRepositoryMock.save(any())).thenReturn(ruleName1);

        RuleNameDto ruleNameDtoResult = ruleNameService.update(ruleNameDto1, 1);

        verify(ruleNameRepositoryMock, times(1)).findById(1);
        verify(ruleNameRepositoryMock, times(1)).save(any());
        assertEquals(ruleNameDto1, ruleNameDtoResult);
    }

    @Test
    void updateIdNoExist() {
        when(ruleNameRepositoryMock.findById(1)).thenReturn(ruleNameOptional0);
        boolean errorTest = true;

        try {
            RuleNameDto ruleNameDtoResult = ruleNameService.update(ruleNameDto1, 1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(ruleNameRepositoryMock, times(1)).findById(1);
        verify(ruleNameRepositoryMock, times(0)).save(any());
        assertFalse(errorTest);
    }

    @Test
    void deleteIdExist() {
        when(ruleNameRepositoryMock.findById(1)).thenReturn(ruleNameOptional1);
        doNothing().when(ruleNameRepositoryMock).deleteById(1);

        ruleNameService.delete(1);

        verify(ruleNameRepositoryMock, times(1)).findById(1);
        verify(ruleNameRepositoryMock, times(1)).deleteById(1);
    }

    @Test
    void deleteIdNoExist() {
        when(ruleNameRepositoryMock.findById(1)).thenReturn(ruleNameOptional0);
        boolean errorTest = true;

        try {
            ruleNameService.delete(1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(ruleNameRepositoryMock, times(1)).findById(1);
        verify(ruleNameRepositoryMock, times(0)).deleteById(1);
        assertFalse(errorTest);
    }
}