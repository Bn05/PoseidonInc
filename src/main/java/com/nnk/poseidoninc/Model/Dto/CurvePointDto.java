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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurvePointDto that = (CurvePointDto) o;

        if (curvePointId != that.curvePointId) return false;
        if (Double.compare(that.term, term) != 0) return false;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = curvePointId;
        temp = Double.doubleToLongBits(term);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
