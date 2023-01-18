package com.nnk.poseidoninc.ControllerWebApp;

import com.nnk.poseidoninc.Model.Dto.RuleNameDto;
import com.nnk.poseidoninc.Service.Implementation.RuleNameServiceImpl;
import org.h2.bnf.Rule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RuleNameControllerWebApp {

    private RuleNameServiceImpl ruleNameService;

    public RuleNameControllerWebApp(RuleNameServiceImpl ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @GetMapping(value = "/RuleName")
    public String home(Model model) {
        List<RuleNameDto> ruleNameDtoList = ruleNameService.findAll();

        model.addAttribute("ruleNameDtoList", ruleNameDtoList);
        model.addAttribute("user", "userTESTA");

        return "ruleName/list";
    }

    @GetMapping(value = "/RuleName/add")
    public String addRuleNamePage(RuleNameDto ruleNameDto) {
        return "ruleName/add";
    }

    @PostMapping(value = "/RuleName/add")
    public String addRuleName(@Validated RuleNameDto ruleNameDto,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.create(ruleNameDto);

        return "redirect:/RuleName";
    }

    @GetMapping(value = "/RuleName/update/{id}")
    public String updateRuleNamePage(@PathVariable(value = "id") int ruleNameId,
                                     Model model) {

        RuleNameDto ruleNameDto = ruleNameService.findById(ruleNameId);

        model.addAttribute("ruleNameDto", ruleNameDto);

        return "ruleName/update";
    }

    @PostMapping(value = "RuleName/update/{id}")
    public String updateRuleName(@PathVariable(value = "id") int ruleNameId,
                                 @Validated RuleNameDto ruleNameDto,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "ruleName/update";
        }
        ruleNameService.update(ruleNameDto, ruleNameId);

        return "redirect:/RuleName";
    }

    @GetMapping(value = "/RuleName/delete/{id}")
    public String delete(@PathVariable(value = "id") int ruleNameId) {

        ruleNameService.delete(ruleNameId);

        return "redirect:/RuleName";
    }
}
