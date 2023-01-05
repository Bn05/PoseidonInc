package com.nnk.poseidoninc.Model.Dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

@Validated
public class BidDto {

    private int bidId;
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

    public BidDto(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;

    }

    public BidDto() {
    }

    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
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
}
