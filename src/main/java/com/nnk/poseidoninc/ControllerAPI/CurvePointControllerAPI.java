package com.nnk.poseidoninc.ControllerAPI;

import com.nnk.poseidoninc.Model.Dto.CurvePointDto;
import com.nnk.poseidoninc.Service.Interface.ICurvePointService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CurvePointControllerAPI {

    private ICurvePointService curvePointService;

    public CurvePointControllerAPI(ICurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @GetMapping(value = "/curvePointList")
    public List<CurvePointDto> findAll() {
        return curvePointService.findAll();
    }

    @PostMapping(value = "/curvePoint")
    public CurvePointDto create(@Validated @RequestBody CurvePointDto curvePointDto) {

        return curvePointService.create(curvePointDto);
    }

    @GetMapping(value = "/curvePoint")
    public CurvePointDto findById(int curvePointId) {

        return curvePointService.findById(curvePointId);
    }

    @PutMapping(value = "/curvePoint")
    public CurvePointDto update(@Validated @RequestBody CurvePointDto curvePointDto,
                                @RequestParam(value = "curvePointId") int curvePointId) {

        return curvePointService.update(curvePointDto, curvePointId);
    }

    @DeleteMapping(value = "/curvePoint")
    public void delete(int curvePointId) {
        curvePointService.delete(curvePointId);
    }
}
