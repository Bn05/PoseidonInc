package com.nnk.poseidoninc.Model.Dto;

import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class BidListDto {

    private int bidListId;
    @NotBlank
    @Size(min = 3, max = 30)
    private String account;
    @NotBlank
    @Size(min = 3, max = 30)
    private String type;

    @NotNull(message = "account is mandatory")
    @Min(value = 5, message = "bidQuantity must be > 5")
    @Max(value = 100, message = "bidQuantity must be < 100")
    private double bidQuantity;

    public BidListDto(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;

    }

    public BidListDto() {
    }

    public int getBidListId() {
        return bidListId;
    }

    public void setBidListId(int bidListId) {
        this.bidListId = bidListId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    @Override
    public String toString() {
        return "BidListDto{" +
                "bidListId=" + bidListId +
                ", account='" + account + '\'' +
                ", type='" + type + '\'' +
                ", bidQuantity=" + bidQuantity +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BidListDto that = (BidListDto) o;

        if (bidListId != that.bidListId) return false;
        if (Double.compare(that.bidQuantity, bidQuantity) != 0) return false;
        if (!Objects.equals(account, that.account)) return false;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = bidListId;
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        temp = Double.doubleToLongBits(bidQuantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
