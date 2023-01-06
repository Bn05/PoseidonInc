package com.nnk.poseidoninc.Service.Implementation;

import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.RatingDto;
import com.nnk.poseidoninc.Model.Rating;
import com.nnk.poseidoninc.Repository.RatingRepository;
import com.nnk.poseidoninc.Service.Interface.IRatingService;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@DynamicUpdate
public class RatingServiceImpl implements IRatingService {

    private RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<RatingDto> findAll() {
        Iterable<Rating> ratingIterable = ratingRepository.findAll();
        List<RatingDto> ratingDtoList = new ArrayList<>();

        for (Rating rating : ratingIterable) {
            ratingDtoList.add(convertRatingToRatingDto(rating));
        }

        return ratingDtoList;
    }

    @Override
    public RatingDto create(RatingDto ratingDto) {

        Rating rating = convertRatingDtoToRating(ratingDto);

        rating = ratingRepository.save(rating);

        return convertRatingToRatingDto(rating);
    }

    @Override
    public RatingDto findById(int ratingId) {
        Optional<Rating> ratingOptional = ratingRepository.findById(ratingId);

        if (ratingOptional.isEmpty()) {
            throw new NotFoundException();
        }

        return convertRatingToRatingDto(ratingOptional.get());
    }

    @Override
    public RatingDto update(RatingDto ratingDto, int ratingId) {
        Optional<Rating> ratingOptional = ratingRepository.findById(ratingId);

        if (ratingOptional.isEmpty()) {
            throw new NotFoundException();
        }

        Rating rating = ratingOptional.get();
        rating.setMoodysRating(ratingDto.getMoodysRating());
        rating.setSandPRating(ratingDto.getSandPRating());
        rating.setFitchRating(ratingDto.getFitchRating());
        rating.setOrderNumber(ratingDto.getOrderNumber());

        rating = ratingRepository.save(rating);

        return convertRatingToRatingDto(rating);
    }

    @Override
    public void delete(int ratingId) {
        Optional<Rating> ratingOptional = ratingRepository.findById(ratingId);

        if (ratingOptional.isEmpty()) {
            throw new NotFoundException();
        }

        ratingRepository.deleteById(ratingId);
    }

    @Override
    public RatingDto convertRatingToRatingDto(Rating rating) {
        RatingDto ratingDto = new RatingDto();

        ratingDto.setRatingId(rating.getRatingId());
        ratingDto.setMoodysRating(rating.getMoodysRating());
        ratingDto.setSandPRating(rating.getSandPRating());
        ratingDto.setFitchRating(rating.getFitchRating());
        ratingDto.setOrderNumber(rating.getOrderNumber());

        return ratingDto;
    }

    @Override
    public Rating convertRatingDtoToRating(RatingDto ratingDto) {
        Rating rating = new Rating();

        rating.setMoodysRating(ratingDto.getMoodysRating());
        rating.setSandPRating(ratingDto.getSandPRating());
        rating.setFitchRating(ratingDto.getFitchRating());
        rating.setOrderNumber(ratingDto.getOrderNumber());

        return rating;
    }
}
