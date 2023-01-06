package com.nnk.poseidoninc.Service.Interface;

import com.nnk.poseidoninc.Model.Dto.RatingDto;
import com.nnk.poseidoninc.Model.Rating;

import java.util.List;

public interface IRatingService {

    List<RatingDto> findAll();

    RatingDto create(RatingDto ratingDto);

    RatingDto findById(int ratingId);

    RatingDto update(RatingDto ratingDto, int ratingId);

    void delete(int ratingId);

    RatingDto convertRatingToRatingDto(Rating rating);

    Rating convertRatingDtoToRating(RatingDto ratingDto);

}
