package com.nnk.poseidoninc.Model.Dto;

import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class TradeDto {

    private int TradeId;
    @NotBlank
    @Size(min = 5, max = 30)
    private String account;
    @NotBlank
    @Size(min = 5, max = 30)
    private String type;
    @NotNull(message = "account is mandatory")
    @Min(value = 5, message = "bidQuantity must be > 5")
    @Max(value = 100, message = "bidQuantity must be < 100")
    private double buyQuantity;

    public TradeDto(String account, String type, double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }
    public TradeDto() {
    }

    public int getTradeId() {
        return TradeId;
    }

    public void setTradeId(int tradeId) {
        TradeId = tradeId;
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

    public double getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(double buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    @Override
    public String toString() {
        return "TradeDto{" +
                "TradeId=" + TradeId +
                ", account='" + account + '\'' +
                ", type='" + type + '\'' +
                ", buyQuantity=" + buyQuantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeDto tradeDto = (TradeDto) o;

        if (TradeId != tradeDto.TradeId) return false;
        if (Double.compare(tradeDto.buyQuantity, buyQuantity) != 0) return false;
        if (!Objects.equals(account, tradeDto.account)) return false;
        return Objects.equals(type, tradeDto.type);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = TradeId;
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        temp = Double.doubleToLongBits(buyQuantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
