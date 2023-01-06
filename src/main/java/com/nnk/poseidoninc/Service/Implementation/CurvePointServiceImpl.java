package com.nnk.poseidoninc.Service.Implementation;

import com.nnk.poseidoninc.Exception.CurvePointNotFoundException;
import com.nnk.poseidoninc.Model.CurvePoint;
import com.nnk.poseidoninc.Model.Dto.CurvePointDto;
import com.nnk.poseidoninc.Repository.CurvePointRepository;
import com.nnk.poseidoninc.Service.Interface.ICurvePointService;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@DynamicUpdate
public class CurvePointServiceImpl implements ICurvePointService {

    private CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @Override
    public List<CurvePointDto> findAll() {
        Iterable<CurvePoint> curvePointIterable = curvePointRepository.findAll();

        List<CurvePointDto> curvePointDtoList = new ArrayList<>();

        for (CurvePoint curvePoint : curvePointIterable) {
            curvePointDtoList.add(convertCurvePointToCurvePointDto(curvePoint));
        }

        return curvePointDtoList;
    }

    @Override
    public CurvePointDto create(CurvePointDto curvePointDto) {
        CurvePoint curvePoint = convertCurvePointDtoToCurvePoint(curvePointDto);

        curvePoint.setCreationDate(LocalDate.now());

        curvePoint = curvePointRepository.save(curvePoint);

        return convertCurvePointToCurvePointDto(curvePoint);
    }

    @Override
    public CurvePointDto findById(int curvePointId) {
        Optional<CurvePoint> curvePointOptional = curvePointRepository.findById(curvePointId);

        if (curvePointOptional.isEmpty()) {
            throw new CurvePointNotFoundException();
        }

        return convertCurvePointToCurvePointDto(curvePointOptional.get());
    }

    @Override
    public CurvePointDto update(CurvePointDto curvePointDto, int curvePointId) {
        Optional<CurvePoint> curvePointOptional = curvePointRepository.findById(curvePointId);

        if (curvePointOptional.isEmpty()) {
            throw new CurvePointNotFoundException();
        }

        CurvePoint curvePoint = curvePointOptional.get();

        curvePoint.setTerm(curvePointDto.getTerm());
        curvePoint.setValue(curvePoint.getValue());

        curvePoint = curvePointRepository.save(curvePoint);

        return convertCurvePointToCurvePointDto(curvePoint);
    }

    @Override
    public void delete(int curvePointId) {
        Optional<CurvePoint> curvePointOptional = curvePointRepository.findById(curvePointId);

        if (curvePointOptional.isEmpty()) {
            throw new CurvePointNotFoundException();
        }

        curvePointRepository.delete(curvePointOptional.get());
    }

    @Override
    public CurvePointDto convertCurvePointToCurvePointDto(CurvePoint curvePoint) {
        CurvePointDto curvePointDto = new CurvePointDto();

        curvePointDto.setCurvePointId(curvePoint.getCurvePointId());
        curvePointDto.setTerm(curvePoint.getTerm());
        curvePointDto.setValue(curvePoint.getValue());

        return curvePointDto;
    }

    @Override
    public CurvePoint convertCurvePointDtoToCurvePoint(CurvePointDto curvePointDto) {
        CurvePoint curvePoint = new CurvePoint();

        curvePoint.setTerm(curvePointDto.getTerm());
        curvePoint.setValue(curvePointDto.getValue());

        return curvePoint;
    }
}
