package com.nnk.poseidoninc.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@DynamicUpdate
@Table(name = "Trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int TradeId;
    private String account;
    private String type;
    private double buyQuantity;
    private double sellQuantity;
    private double buyPrice;
    private double sellPrice;
    private LocalDate tradeDate;
    private String security;
    private String status;
    private String trader;
    private String benchmark;
    private String book;
    private String creationName;
    private LocalDate creationDate;
    private String revisionName;
    private LocalDate revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

}
