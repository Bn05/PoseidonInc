package com.nnk.poseidoninc.Service.Implementation;

import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.RatingDto;
import com.nnk.poseidoninc.Model.Rating;
import com.nnk.poseidoninc.Repository.RatingRepository;
import com.nnk.poseidoninc.Service.Interface.IRatingService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@DynamicUpdate
public class RatingServiceImpl implements IRatingService {

    private static final Logger logger = LogManager.getLogger(RatingServiceImpl.class);

    private RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * @return List<RatingDto>
     *     return all RatingDto in db
     */
    @Override
    public List<RatingDto> findAll() {
        Iterable<Rating> ratingIterable = ratingRepository.findAll();
        List<RatingDto> ratingDtoList = new ArrayList<>();

        for (Rating rating : ratingIterable) {
            ratingDtoList.add(convertRatingToRatingDto(rating));
        }

        return ratingDtoList;
    }

    /**
     * @param ratingDto we want ta add to db
     * @return RatingDto added
     * Create new Rating in db
     */
    @Override
    public RatingDto create(RatingDto ratingDto) {

        Rating rating = convertRatingDtoToRating(ratingDto);

        rating = ratingRepository.save(rating);

        return convertRatingToRatingDto(rating);
    }

    /**
     * @param ratingId of Rating we are looking for
     * @return RatingDto
     * Find RatingDto By Id
     */
    @Override
    public RatingDto findById(int ratingId) {
        Optional<Rating> ratingOptional = ratingRepository.findById(ratingId);

        if (ratingOptional.isEmpty()) {
            logger.warn("NotFoundRatingWithThisId");
            throw new NotFoundException();
        }

        return convertRatingToRatingDto(ratingOptional.get());
    }

    /**
     * @param ratingDto with new param
     * @param ratingId of Rating we want update
     * @return Rating with modif
     */
    @Override
    public RatingDto update(RatingDto ratingDto, int ratingId) {
        Optional<Rating> ratingOptional = ratingRepository.findById(ratingId);

        //verify Rating exist.
        if (ratingOptional.isEmpty()) {
            logger.warn("NotFoundRatingWithThisId");
            throw new NotFoundException();
        }

       //Update Rating
        Rating rating = ratingOptional.get();
        rating.setMoodysRating(ratingDto.getMoodysRating());
        rating.setSandPRating(ratingDto.getSandPRating());
        rating.setFitchRating(ratingDto.getFitchRating());
        rating.setOrderNumber(ratingDto.getOrderNumber());

        rating = ratingRepository.save(rating);

        return convertRatingToRatingDto(rating);
    }

    /**
     * @param ratingId of Rating we want delete
     */
    @Override
    public void delete(int ratingId) {
        Optional<Rating> ratingOptional = ratingRepository.findById(ratingId);

        //verify Rating Exist
        if (ratingOptional.isEmpty()) {
            logger.warn("NotFoundRatingWithThisId");
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
