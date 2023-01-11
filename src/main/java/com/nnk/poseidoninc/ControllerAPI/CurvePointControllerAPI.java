package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.Dto.CurvePointDto;
import com.nnk.poseidoninc.Service.Interface.ICurvePointService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CurvePointControllerAPI {

    private ICurvePointService curvePointService;

    private static final Logger logger = LogManager.getLogger(CurvePointControllerAPI.class);

    public CurvePointControllerAPI(ICurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @GetMapping(value = "/curvePointList")
    public List<CurvePointDto> findAll() {
        logger.info("Request GET /curvePointList");
        List<CurvePointDto> curvePointDtoListValidation = curvePointService.findAll();
        logger.trace("Response to Request : " + curvePointDtoListValidation.toString());
        return curvePointDtoListValidation;
    }

    @PostMapping(value = "/curvePoint")
    public CurvePointDto create(@Validated @RequestBody CurvePointDto curvePointDto) {
        logger.info("Request POST /curePoint, BodyRequest = " + curvePointDto.toString());
        CurvePointDto curvePointDtoValidation = curvePointService.create(curvePointDto);
        logger.trace("Response to Request : " + curvePointDtoValidation.toString());
        return curvePointDtoValidation;

    }

    @GetMapping(value = "/curvePoint")
    public CurvePointDto findById(@RequestParam(value = "curvePointId") int curvePointId) {
        logger.info("Request GET /curvePoint, ParamRequest = " + curvePointId);
        CurvePointDto curvePointDtoValidation = curvePointService.findById(curvePointId);
        logger.trace("Response to Request : " + curvePointDtoValidation.toString());
        return curvePointDtoValidation;
    }

    @PutMapping(value = "/curvePoint")
    public CurvePointDto update(@Validated @RequestBody CurvePointDto curvePointDto,
                                @RequestParam(value = "curvePointId") int curvePointId) {
        logger.info("Request PUT /curvePoint, ParamRequest = " + curvePointId + " || BodyRequest : " + curvePointDto.toString());
        CurvePointDto curvePointDtoValidation = curvePointService.update(curvePointDto, curvePointId);
        logger.trace("Response to Request : " + curvePointDtoValidation.toString());
        return curvePointDtoValidation;
    }

    @DeleteMapping(value = "/curvePoint")
    public void delete(@RequestParam(value = "curvePointId") int curvePointId) {
        logger.info("Request to DELETE /curvePoint, ParamRequest = "+curvePointId);
        curvePointService.delete(curvePointId);
        logger.trace("Delete validated");
    }
}
