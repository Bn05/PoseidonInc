package com.nnk.poseidoninc.Service.Interface;

import com.nnk.poseidoninc.Model.CurvePoint;
import com.nnk.poseidoninc.Model.Dto.CurvePointDto;

import java.util.List;

public interface ICurvePointService {

    List<CurvePointDto> findAll();

    CurvePointDto create(CurvePointDto curvePointDto);

    CurvePointDto findById(int curvePointId);

    CurvePointDto update(CurvePointDto curvePointDto, int curvePointId);

    void delete(int curvePointId);

    CurvePointDto convertCurvePointToCurvePointDto(CurvePoint curvePoint);

    CurvePoint convertCurvePointDtoToCurvePoint(CurvePointDto curvePointDto);
}
