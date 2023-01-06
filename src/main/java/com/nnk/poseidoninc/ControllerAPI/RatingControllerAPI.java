package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.Dto.RatingDto;
import com.nnk.poseidoninc.Service.Interface.IRatingService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RatingControllerAPI {

    private IRatingService ratingService;

    public RatingControllerAPI(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(value = "/ratingList")
    public List<RatingDto> findall() {
        return ratingService.findAll();

    }

    @PostMapping(value = "/rating")
    public RatingDto create(@Validated @RequestBody RatingDto ratingDto) {
        return ratingService.create(ratingDto);
    }

    @GetMapping(value = "/rating")
    public RatingDto findbyId(@RequestParam(value = "ratingId") int ratingId) {
        return ratingService.findById(ratingId);
    }

    @PutMapping(value = "/rating")
    public RatingDto update(@Validated @RequestBody RatingDto ratingDto,
                            @RequestParam(value = "ratingId") int ratingId) {
        return ratingService.update(ratingDto, ratingId);
    }

    @DeleteMapping(value = "/rating")
    public void delete(int ratingId) {
        ratingService.delete(ratingId);
    }
}
