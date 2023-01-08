package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.Dto.RuleNameDto;
import com.nnk.poseidoninc.Service.Interface.IRuleNameService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RuleNameControllerAPI {

    private IRuleNameService ruleNameService;

    public RuleNameControllerAPI(IRuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @GetMapping(value = "/ruleNameList")
    public List<RuleNameDto> findAll() {
        return ruleNameService.findAll();
    }

    @PostMapping(value = "/ruleName")
    public RuleNameDto create(@Validated @RequestBody RuleNameDto ruleNameDto) {
        return ruleNameService.create(ruleNameDto);
    }

    @GetMapping(value = "/ruleName")
    public RuleNameDto findById(@RequestParam(value = "ruleNameId") int ruleNameId) {
        return ruleNameService.findById(ruleNameId);
    }

    @PutMapping(value = "/ruleName")
    public RuleNameDto update(@Validated @RequestBody RuleNameDto ruleNameDto,
                              @RequestParam(value = "ruleNameId") int ruleNameId) {
        return ruleNameService.update(ruleNameDto, ruleNameId);
    }

    @DeleteMapping(value = "/ruleName")
    public void delete(int ruleNameId) {
        ruleNameService.delete(ruleNameId);
    }

}
