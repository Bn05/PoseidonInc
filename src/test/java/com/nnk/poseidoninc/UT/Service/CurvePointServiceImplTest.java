package com.nnk.poseidoninc.UT.Service;

import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.CurvePoint;
import com.nnk.poseidoninc.Model.Dto.CurvePointDto;
import com.nnk.poseidoninc.Repository.CurvePointRepository;
import com.nnk.poseidoninc.Service.Implementation.CurvePointServiceImpl;
import org.junit.jupiter.api.BeforeAll;
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
class CurvePointServiceImplTest {

    @InjectMocks
    CurvePointServiceImpl curvePointService;

    @Mock
    CurvePointRepository curvePointRepositoryMock;

    CurvePointDto curvePointDto1 = new CurvePointDto();
    CurvePointDto curvePointDto2 = new CurvePointDto();
    CurvePointDto curvePointDtoUpdate = new CurvePointDto();

    CurvePoint curvePoint1 = new CurvePoint();
    CurvePoint curvePoint2 = new CurvePoint();
    CurvePoint curvePointUpdate = new CurvePoint();

    Optional<CurvePoint> curvePointOptional0 = Optional.empty();
    Optional<CurvePoint> curvePointOptional1;
    Optional<CurvePoint> curvePointOptional2;
    List<CurvePoint> curvePointListIterable = new ArrayList<>();
    List<CurvePointDto> curvePointDtoList = new ArrayList<>();

    @BeforeAll
    void buildTest() {

        curvePoint1.setCurvePointId(1);
        curvePoint1.setTerm(11);
        curvePoint1.setValue(11);
        curvePointOptional1 = Optional.of(curvePoint1);

        curvePoint2.setCurvePointId(2);
        curvePoint2.setTerm(22);
        curvePoint2.setValue(22);
        curvePointOptional2 = Optional.of(curvePoint2);

        curvePointListIterable.add(curvePoint1);
        curvePointListIterable.add(curvePoint2);

        curvePointDto1.setCurvePointId(1);
        curvePointDto1.setTerm(11);
        curvePointDto1.setValue(11);

        curvePointDto2.setCurvePointId(2);
        curvePointDto2.setTerm(22);
        curvePointDto2.setValue(22);

        curvePointDtoList.add(curvePointDto1);
        curvePointDtoList.add(curvePointDto2);

        curvePointUpdate.setCurvePointId(1);
        curvePointUpdate.setTerm(33);
        curvePointUpdate.setValue(33);

        curvePointDtoUpdate.setCurvePointId(1);
        curvePointDtoUpdate.setTerm(33);
        curvePointDtoUpdate.setValue(33);
    }

    @Test
    void findAll() {
        when(curvePointRepositoryMock.findAll()).thenReturn(curvePointListIterable);

        List<CurvePointDto> curvePointDtoListResult = curvePointService.findAll();

        verify(curvePointRepositoryMock, times(1)).findAll();
        assertEquals(curvePointDtoList, curvePointDtoListResult);
    }

    @Test
    void create() {
        when(curvePointRepositoryMock.save(any())).thenReturn(curvePoint1);

        CurvePointDto curvePointDtoResult = curvePointService.create(curvePointDto1);

        verify(curvePointRepositoryMock, times(1)).save(any());
        assertEquals(curvePointDto1, curvePointDtoResult);
    }

    @Test
    void findByIdExist() {
        when(curvePointRepositoryMock.findById(1)).thenReturn(curvePointOptional1);

        CurvePointDto curvePointDtoResult = curvePointService.findById(1);

        verify(curvePointRepositoryMock, times(1)).findById(1);
        assertEquals(curvePointDto1, curvePointDtoResult);
    }

    @Test
    void findByIdNoExist() {
        when(curvePointRepositoryMock.findById(1)).thenReturn(curvePointOptional0);
        boolean errorTest = true;
        try {
            CurvePointDto curvePointDtoResult = curvePointService.findById(1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(curvePointRepositoryMock, times(1)).findById(1);
        assertFalse(errorTest);
    }

    @Test
    void updateExistId() {
        when(curvePointRepositoryMock.findById(any())).thenReturn(curvePointOptional1);
        when(curvePointRepositoryMock.save(any())).thenReturn(curvePointUpdate);

        CurvePointDto curvePointDtoResult = curvePointService.update(curvePointDtoUpdate, 1);

        verify(curvePointRepositoryMock, times(1)).findById(1);
        verify(curvePointRepositoryMock, times(1)).save(any());
        assertEquals(curvePointDtoUpdate, curvePointDtoResult);
    }

    @Test
    void updateNoExistId() {
        when(curvePointRepositoryMock.findById(any())).thenReturn(curvePointOptional0);

        boolean errorTest = true;
        try {
            CurvePointDto curvePointDtoResult = curvePointService.update(curvePointDtoUpdate, 1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(curvePointRepositoryMock, times(1)).findById(1);
        verify(curvePointRepositoryMock, times(0)).save(any());
        assertFalse(errorTest);
    }

    @Test
    void deleteExistId() {
        when(curvePointRepositoryMock.findById(1)).thenReturn(curvePointOptional1);
        doNothing().when(curvePointRepositoryMock).deleteById(1);

        curvePointService.delete(1);

        verify(curvePointRepositoryMock, times(1)).deleteById(1);

    }

    @Test
    void deleteNOExistId() {
        when(curvePointRepositoryMock.findById(1)).thenReturn(curvePointOptional0);
        boolean errorTest = true;

        try {
            curvePointService.delete(1);
        } catch (NotFoundException e) {
            errorTest = false;
        }

        verify(curvePointRepositoryMock, times(0)).deleteById(1);
        assertFalse(errorTest);

    }


}