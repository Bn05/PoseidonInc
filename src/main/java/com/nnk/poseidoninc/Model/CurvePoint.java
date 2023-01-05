package com.nnk.poseidoninc.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@DynamicUpdate
@Table(name = "CurvePoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int curvePointId;
    private LocalDate asOfDate;
    private double term;
    private double value;
    private LocalDate creationDate;
}
