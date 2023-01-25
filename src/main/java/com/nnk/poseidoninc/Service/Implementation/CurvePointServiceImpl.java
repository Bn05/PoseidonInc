package com.nnk.poseidoninc.Service.Implementation;

import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.CurvePoint;
import com.nnk.poseidoninc.Model.Dto.CurvePointDto;
import com.nnk.poseidoninc.Repository.CurvePointRepository;
import com.nnk.poseidoninc.Service.Interface.ICurvePointService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(CurvePointServiceImpl.class);

    private CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * @return List<CurvePointDto> all curvePoint in db
     */
    @Override
    public List<CurvePointDto> findAll() {
        Iterable<CurvePoint> curvePointIterable = curvePointRepository.findAll();

        List<CurvePointDto> curvePointDtoList = new ArrayList<>();

        for (CurvePoint curvePoint : curvePointIterable) {
            curvePointDtoList.add(convertCurvePointToCurvePointDto(curvePoint));
        }

        return curvePointDtoList;
    }

    /**
     * @param curvePointDto we want to add to db
     * @return CurvePoint added
     * Create new CurvePoint in db
     */
    @Override
    public CurvePointDto create(CurvePointDto curvePointDto) {
        CurvePoint curvePoint = convertCurvePointDtoToCurvePoint(curvePointDto);

        curvePoint.setCreationDate(LocalDate.now());

        curvePoint = curvePointRepository.save(curvePoint);

        return convertCurvePointToCurvePointDto(curvePoint);
    }

    /**
     * @param curvePointId of CurvePoint we are looking for
     * @return CurvePointDto
     * Find CurvePoint By Id
     */
    @Override
    public CurvePointDto findById(int curvePointId) {
        Optional<CurvePoint> curvePointOptional = curvePointRepository.findById(curvePointId);

        if (curvePointOptional.isEmpty()) {
            logger.warn("NotFoundCurvePointWithThisId");
            throw new NotFoundException();
        }

        return convertCurvePointToCurvePointDto(curvePointOptional.get());
    }

    /**
     * @param curvePointDto with new param
     * @param curvePointId  of CurvePoint we want update
     * @return CurvePoint with modif
     */
    @Override
    public CurvePointDto update(CurvePointDto curvePointDto, int curvePointId) {
        Optional<CurvePoint> curvePointOptional = curvePointRepository.findById(curvePointId);

        //verify CurvePoint exist.
        if (curvePointOptional.isEmpty()) {
            logger.warn("NotFoundCurvePointWithThisId");
            throw new NotFoundException();
        }

        CurvePoint curvePoint = curvePointOptional.get();
        //update CurvePoint
        curvePoint.setTerm(curvePointDto.getTerm());
        curvePoint.setValue(curvePointDto.getValue());

        curvePoint = curvePointRepository.save(curvePoint);

        return convertCurvePointToCurvePointDto(curvePoint);
    }

    /**
     * @param curvePointId of CurvePoint we want delete
     */
    @Override
    public void delete(int curvePointId) {
        Optional<CurvePoint> curvePointOptional = curvePointRepository.findById(curvePointId);

        //verify CurvePoint exist
        if (curvePointOptional.isEmpty()) {
            logger.warn("NotFoundCurvePointWithThisId");
            throw new NotFoundException();
        }

        curvePointRepository.deleteById(curvePointId);
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
