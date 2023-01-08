package com.nnk.poseidoninc.Service.Interface;

import com.nnk.poseidoninc.Model.Dto.RuleNameDto;
import com.nnk.poseidoninc.Model.RuleName;

import java.util.List;

public interface IRuleNameService {

    List<RuleNameDto> findAll();
    RuleNameDto create(RuleNameDto ruleNameDto);
    RuleNameDto findById(int ruleNameId);
    RuleNameDto update(RuleNameDto ruleNameDto, int ruleNameId);
    void delete(int ruleNameId);
    RuleNameDto convertRuleNameToRuleNameDto(RuleName ruleName);
    RuleName convertRuleNameDtoToRuleName(RuleNameDto ruleNameDto);


}
