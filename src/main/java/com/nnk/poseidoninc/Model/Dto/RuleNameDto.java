package com.nnk.poseidoninc.Model.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public class RuleNameDto {

    private int ruleNameId;
    @NotBlank
    @Size(max = 125)
    private String name;
    @NotBlank
    @Size(max = 125)
    private String description;
    @NotBlank
    @Size(max = 125)
    private String json;
    @NotBlank
    @Size(max = 512)
    private String template;
    @NotBlank
    @Size(max = 125)
    private String sqlStr;
    @NotBlank
    @Size(max = 125)
    private String sqlPart;

    public RuleNameDto(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    public RuleNameDto() {
    }

    public int getRuleNameId() {
        return ruleNameId;
    }

    public void setRuleNameId(int ruleNameId) {
        this.ruleNameId = ruleNameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }
}
