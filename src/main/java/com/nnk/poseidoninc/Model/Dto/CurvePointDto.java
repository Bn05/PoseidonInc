package com.nnk.poseidoninc.Model.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public class CurvePointDto {
    private int curvePointId;
    @NotNull(message = "account is mandatory")
    @Min(value = 5, message = "bidQuantity must be > 5")
    @Max(value = 100, message = "bidQuantity must be < 100")
    private double term;

    @NotNull(message = "account is mandatory")
    @Min(value = 5, message = "bidQuantity must be > 5")
    @Max(value = 100, message = "bidQuantity must be < 100")
    private double value;

    public CurvePointDto(double term, double value) {
        this.term = term;
        this.value = value;
    }

    public CurvePointDto() {
    }


    public int getCurvePointId() {
        return curvePointId;
    }

    public void setCurvePointId(int curvePointId) {
        this.curvePointId = curvePointId;
    }

    public double getTerm() {
        return term;
    }

    public void setTerm(double term) {
        this.term = term;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CurvePointDto{" +
                "curvePointId=" + curvePointId +
                ", term=" + term +
                ", value=" + value +
                '}';
    }
}
