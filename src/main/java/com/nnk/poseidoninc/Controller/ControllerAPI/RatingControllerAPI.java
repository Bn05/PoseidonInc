package com.nnk.poseidoninc.Controller.ControllerAPI;

import com.nnk.poseidoninc.Model.Dto.RatingDto;
import com.nnk.poseidoninc.Service.Interface.IRatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RatingControllerAPI {

    private IRatingService ratingService;

    private static final Logger logger = LogManager.getLogger(RatingControllerAPI.class);

    public RatingControllerAPI(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(value = "/ratingList")
    public List<RatingDto> findAll() {
        logger.info("Request GET /ratingList");
        List<RatingDto> ratingDtoListValidation = ratingService.findAll();
        logger.trace("Response to Request : " + ratingDtoListValidation.toString());
        return ratingDtoListValidation;
    }

    @PostMapping(value = "/rating")
    public RatingDto create(@Validated @RequestBody RatingDto ratingDto) {
        logger.info("Request POST /rating, BodyRequest : " + ratingDto.toString());
        RatingDto ratingDtoValidation = ratingService.create(ratingDto);
        logger.trace("Response to Request : " + ratingDtoValidation.toString());
        return ratingDtoValidation;
    }

    @GetMapping(value = "/rating")
    public RatingDto findById(@RequestParam(value = "ratingId") int ratingId) {
        logger.info("Request GET /rating, RequestParam ratingId = " + ratingId);
        RatingDto ratingDtoValidation = ratingService.findById(ratingId);
        logger.trace("Response to Request : " + ratingDtoValidation.toString());
        return ratingDtoValidation;
    }

    @PutMapping(value = "/rating")
    public RatingDto update(@Validated @RequestBody RatingDto ratingDto,
                            @RequestParam(value = "ratingId") int ratingId) {
        logger.info("Request PUT /rating, RequestParam ratingId = " + ratingId + " || RequestBody : " + ratingDto.toString());
        RatingDto ratingDtoValidation = ratingService.update(ratingDto, ratingId);
        logger.trace("Response to Request : " + ratingDtoValidation.toString());
        return ratingDtoValidation;
    }

    @DeleteMapping(value = "/rating")
    public void delete(@RequestParam(value = "ratingId") int ratingId) {
        logger.info("Request DELETE /rating, RequestParam : ratingId = " + ratingId);
        ratingService.delete(ratingId);
        logger.trace("Delete validated");
    }
}
