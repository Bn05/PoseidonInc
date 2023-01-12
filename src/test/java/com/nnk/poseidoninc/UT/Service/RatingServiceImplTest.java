package com.nnk.poseidoninc.UT.Service;

import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.Dto.RatingDto;
import com.nnk.poseidoninc.Model.Rating;
import com.nnk.poseidoninc.Repository.RatingRepository;
import com.nnk.poseidoninc.Service.Implementation.RatingServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RatingServiceImplTest {

    @InjectMocks
    RatingServiceImpl ratingService;

    @Mock
    RatingRepository ratingRepositoryMock;

    RatingDto ratingDto1 = new RatingDto();
    RatingDto ratingDto2 = new RatingDto();
    RatingDto ratingDtoUpdate = new RatingDto();
    Rating rating1 = new Rating();
    Rating rating2 = new Rating();
    Rating ratingUpdate = new Rating();

    Optional<Rating> ratingOptional0 = Optional.empty();
    Optional<Rating> ratingOptional1;
    Optional<Rating> ratingOptional2;

    List<Rating> ratingIterable = new ArrayList<>();
    List<RatingDto> ratingDtoList = new ArrayList<>();

    @BeforeAll
    void buildTest() {

        rating1.setRatingId(1);
        rating1.setMoodysRating("moodysTest1");
        rating1.setSandPRating("SandPRTest1");
        rating1.setFitchRating("fitchTest1");
        rating1.setOrderNumber(1);
        ratingOptional1 = Optional.of(rating1);

        rating2.setRatingId(2);
        rating2.setMoodysRating("moodysTest2");
        rating2.setSandPRating("SandPRTest2");
        rating2.setFitchRating("fitchTest2");
        rating2.setOrderNumber(2);
        ratingOptional2 = Optional.of(rating2);

        ratingIterable.add(rating1);
        ratingIterable.add(rating2);

        ratingDto1.setRatingId(1);
        ratingDto1.setMoodysRating("moodysTest1");
        ratingDto1.setSandPRating("SandPRTest1");
        ratingDto1.setFitchRating("fitchTest1");
        ratingDto1.setOrderNumber(1);

        ratingDto2.setRatingId(2);
        ratingDto2.setMoodysRating("moodysTest2");
        ratingDto2.setSandPRating("SandPRTest2");
        ratingDto2.setFitchRating("fitchTest2");
        ratingDto2.setOrderNumber(2);

        ratingDtoList.add(ratingDto1);
        ratingDtoList.add(ratingDto2);

        ratingUpdate.setRatingId(1);
        ratingUpdate.setMoodysRating("moodysTestUpdate");
        ratingUpdate.setSandPRating("SandPRTestUpdate");
        ratingUpdate.setFitchRating("fitchTestUpdate");
        ratingUpdate.setOrderNumber(3);

        ratingDtoUpdate.setRatingId(1);
        ratingDtoUpdate.setMoodysRating("moodysTestUpdate");
        ratingDtoUpdate.setSandPRating("SandPRTestUpdate");
        ratingDtoUpdate.setFitchRating("fitchTestUpdate");
        ratingDtoUpdate.setOrderNumber(3);
    }

    @Test
    void findAll() {
        when(ratingRepositoryMock.findAll()).thenReturn(ratingIterable);

        List<RatingDto> ratingDtoListResult = ratingService.findAll();

        verify(ratingRepositoryMock, times(1)).findAll();
        assertEquals(ratingDtoList, ratingDtoListResult);
    }

    @Test
    void create() {
        when(ratingRepositoryMock.save(any())).thenReturn(rating1);

        RatingDto ratingDtoResult = ratingService.create(ratingDto1);

        verify(ratingRepositoryMock, times(1)).save(any());
        assertEquals(ratingDto1, ratingDtoResult);
    }

    @Test
    void findByIdExist() {
        when(ratingRepositoryMock.findById(1)).thenReturn(ratingOptional1);

        RatingDto ratingDtoResult = ratingService.findById(1);

        verify(ratingRepositoryMock, times(1)).findById(any());
        assertEquals(ratingDto1, ratingDtoResult);
    }

    @Test
    void findByIdNoExist() {
        when(ratingRepositoryMock.findById(1)).thenReturn(ratingOptional0);
        boolean errorTest = true;
        try {
            RatingDto ratingDtoResult = ratingService.findById(1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(ratingRepositoryMock, times(0)).save(any());
        assertFalse(errorTest);
    }

    @Test
    void updateIdExist() {
        when(ratingRepositoryMock.findById(1)).thenReturn(ratingOptional1);
        when(ratingRepositoryMock.save(any())).thenReturn(rating1);

        RatingDto ratingDtoResult = ratingService.update(ratingDto1, 1);

        verify(ratingRepositoryMock, times(1)).findById(1);
        verify(ratingRepositoryMock, times(1)).save(any());
        assertEquals(ratingDto1, ratingDtoResult);
    }

    @Test
    void updateIdNoExist() {
        when(ratingRepositoryMock.findById(1)).thenReturn(ratingOptional0);
        boolean errorTest = true;

        try {
            RatingDto ratingDtoResult = ratingService.update(ratingDto1, 1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(ratingRepositoryMock, times(1)).findById(1);
        verify(ratingRepositoryMock, times(0)).save(any());
        assertFalse(errorTest);
    }

    @Test
    void deleteIdExist() {
        when(ratingRepositoryMock.findById(1)).thenReturn(ratingOptional1);
        doNothing().when(ratingRepositoryMock).deleteById(1);

        ratingService.delete(1);

        verify(ratingRepositoryMock, times(1)).findById(1);
        verify(ratingRepositoryMock, times(1)).deleteById(1);
    }

    @Test
    void deleteIdNoExist() {
        when(ratingRepositoryMock.findById(1)).thenReturn(ratingOptional0);
        boolean errorTest = true;

        try {
            ratingService.delete(1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(ratingRepositoryMock, times(1)).findById(1);
        verify(ratingRepositoryMock, times(0)).deleteById(1);
        assertFalse(errorTest);
    }


}