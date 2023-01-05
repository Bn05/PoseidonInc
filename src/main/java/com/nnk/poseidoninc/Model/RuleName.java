package com.nnk.poseidoninc.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "RuleName")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ruleNameId;
    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;

}
