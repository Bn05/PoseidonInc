package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.Dto.RuleNameDto;
import com.nnk.poseidoninc.Service.Interface.IRuleNameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RuleNameControllerAPI {

    private static final Logger logger = LogManager.getLogger(RuleNameControllerAPI.class);

    private IRuleNameService ruleNameService;

    public RuleNameControllerAPI(IRuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @GetMapping(value = "/ruleNameList")
    public List<RuleNameDto> findAll() {
        logger.info("Request GET /ruleNameList");
        List<RuleNameDto> ruleNameDtoListValidation = ruleNameService.findAll();
        logger.trace("Response to Request : " + ruleNameDtoListValidation.toString());
        return ruleNameDtoListValidation;
    }

    @PostMapping(value = "/ruleName")
    public RuleNameDto create(@Validated @RequestBody RuleNameDto ruleNameDto) {
        logger.info("Request POST /ruleName, RequestBody :" + ruleNameDto.toString());
        RuleNameDto ruleNameDtoValidation = ruleNameService.create(ruleNameDto);
        logger.trace("Response to Request : " + ruleNameDtoValidation.toString());
        return ruleNameDtoValidation;
    }

    @GetMapping(value = "/ruleName")
    public RuleNameDto findById(@RequestParam(value = "ruleNameId") int ruleNameId) {
        logger.info("Request GET /ruleName, RequestParam ruleNameId = " + ruleNameId);
        RuleNameDto ruleNameDtoValidation = ruleNameService.findById(ruleNameId);
        logger.trace("Response to Request : " + ruleNameDtoValidation.toString());
        return ruleNameDtoValidation;
    }

    @PutMapping(value = "/ruleName")
    public RuleNameDto update(@Validated @RequestBody RuleNameDto ruleNameDto,
                              @RequestParam(value = "ruleNameId") int ruleNameId) {
        logger.info("Request PUT /ruleName RequestParam ruleNameId = " + ruleNameId + " || RequestBody : " + ruleNameDto.toString());
        RuleNameDto ruleNameDtoValidation = ruleNameService.update(ruleNameDto, ruleNameId);
        logger.trace("Response to Request : " + ruleNameDtoValidation.toString());
        return ruleNameDtoValidation;
    }

    @DeleteMapping(value = "/ruleName")
    public void delete(@RequestParam(value = "ruleNameId") int ruleNameId) {
        logger.info("Request DELETE /ruleName, RequestParam ruleNameId = "+ruleNameId);
        ruleNameService.delete(ruleNameId);
        logger.trace("Delete validated");
    }

}
