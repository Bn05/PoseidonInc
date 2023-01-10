package com.nnk.poseidoninc.Model.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class RatingDto {
    private int ratingId;
    @NotBlank
    @Size(min = 5, max = 125)
    private String moodysRating;
    @NotBlank
    @Size(min = 5, max = 125)
    private String sandPRating;
    @NotBlank
    @Size(min = 5, max = 125)
    private String fitchRating;
    @NotNull
    @Min(value = 1)
    private int orderNumber;

    public RatingDto(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    public RatingDto() {
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "RatingDto{" +
                "ratingId=" + ratingId +
                ", moodysRating='" + moodysRating + '\'' +
                ", sandPRating='" + sandPRating + '\'' +
                ", fitchRating='" + fitchRating + '\'' +
                ", orderNumber=" + orderNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingDto ratingDto = (RatingDto) o;

        if (ratingId != ratingDto.ratingId) return false;
        if (orderNumber != ratingDto.orderNumber) return false;
        if (!Objects.equals(moodysRating, ratingDto.moodysRating))
            return false;
        if (!Objects.equals(sandPRating, ratingDto.sandPRating))
            return false;
        return Objects.equals(fitchRating, ratingDto.fitchRating);
    }

    @Override
    public int hashCode() {
        int result = ratingId;
        result = 31 * result + (moodysRating != null ? moodysRating.hashCode() : 0);
        result = 31 * result + (sandPRating != null ? sandPRating.hashCode() : 0);
        result = 31 * result + (fitchRating != null ? fitchRating.hashCode() : 0);
        result = 31 * result + orderNumber;
        return result;
    }
}
