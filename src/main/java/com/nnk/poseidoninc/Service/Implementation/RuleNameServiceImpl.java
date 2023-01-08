package com.nnk.poseidoninc.Service.Implementation;

import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.RuleNameDto;
import com.nnk.poseidoninc.Model.RuleName;
import com.nnk.poseidoninc.Repository.RuleNameRepository;
import com.nnk.poseidoninc.Service.Interface.IRuleNameService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RuleNameServiceImpl implements IRuleNameService {

    private RuleNameRepository ruleNameRepository;

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    public List<RuleNameDto> findAll() {
        Iterable<RuleName> ruleNameIterable = ruleNameRepository.findAll();
        List<RuleNameDto> ruleNameDtoList = new ArrayList<>();

        for (RuleName ruleName : ruleNameIterable) {
            ruleNameDtoList.add(convertRuleNameToRuleNameDto(ruleName));
        }

        return ruleNameDtoList;
    }

    @Override
    public RuleNameDto create(RuleNameDto ruleNameDto) {
        RuleName ruleName = convertRuleNameDtoToRuleName(ruleNameDto);
        ruleName = ruleNameRepository.save(ruleName);

        return convertRuleNameToRuleNameDto(ruleName);
    }

    @Override
    public RuleNameDto findById(int ruleNameId) {
        Optional<RuleName> ruleNameOptional = ruleNameRepository.findById(ruleNameId);

        if (ruleNameOptional.isEmpty()) {
            throw new NotFoundException();
        }

        return convertRuleNameToRuleNameDto(ruleNameOptional.get());
    }

    @Override
    public RuleNameDto update(RuleNameDto ruleNameDto, int ruleNameId) {
        Optional<RuleName> ruleNameOptional = ruleNameRepository.findById(ruleNameId);

        if (ruleNameOptional.isEmpty()) {
            throw new NotFoundException();
        }

        RuleName ruleName = ruleNameOptional.get();
        ruleName.setName(ruleNameDto.getName());
        ruleName.setDescription(ruleNameDto.getDescription());
        ruleName.setJson(ruleNameDto.getJson());
        ruleName.setTemplate(ruleNameDto.getTemplate());
        ruleName.setSqlStr(ruleNameDto.getSqlStr());
        ruleName.setSqlPart(ruleNameDto.getSqlPart());

        ruleName = ruleNameRepository.save(ruleName);

        return convertRuleNameToRuleNameDto(ruleName);
    }

    @Override
    public void delete(int ruleNameId) {
        Optional<RuleName> ruleNameOptional = ruleNameRepository.findById(ruleNameId);

        if (ruleNameOptional.isEmpty()) {
            throw new NotFoundException();
        }

        ruleNameRepository.deleteById(ruleNameId);
    }

    @Override
    public RuleNameDto convertRuleNameToRuleNameDto(RuleName ruleName) {
        RuleNameDto ruleNameDto = new RuleNameDto();

        ruleNameDto.setRuleNameId(ruleName.getRuleNameId());
        ruleNameDto.setName(ruleName.getName());
        ruleNameDto.setDescription(ruleName.getDescription());
        ruleNameDto.setJson(ruleName.getJson());
        ruleNameDto.setTemplate(ruleName.getTemplate());
        ruleNameDto.setSqlStr(ruleName.getSqlStr());
        ruleNameDto.setSqlPart(ruleName.getSqlPart());

        return ruleNameDto;


    }

    @Override
    public RuleName convertRuleNameDtoToRuleName(RuleNameDto ruleNameDto) {
        RuleName ruleName = new RuleName();

        ruleName.setName(ruleNameDto.getName());
        ruleName.setDescription(ruleNameDto.getDescription());
        ruleName.setJson(ruleNameDto.getJson());
        ruleName.setTemplate(ruleNameDto.getTemplate());
        ruleName.setSqlStr(ruleNameDto.getSqlStr());
        ruleName.setSqlPart(ruleNameDto.getSqlPart());

        return ruleName;
    }
}
