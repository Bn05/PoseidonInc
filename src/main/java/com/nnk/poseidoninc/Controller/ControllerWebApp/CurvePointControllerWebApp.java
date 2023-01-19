package com.nnk.poseidoninc.Controller.ControllerWebApp;

import com.nnk.poseidoninc.Model.Dto.CurvePointDto;
import com.nnk.poseidoninc.Service.Implementation.CurvePointServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CurvePointControllerWebApp {

    private CurvePointServiceImpl curvePointService;

    public CurvePointControllerWebApp(CurvePointServiceImpl curvePointService) {
        this.curvePointService = curvePointService;
    }

    @GetMapping(value = "/CurvePoint")
    public String home(Model model) {
        List<CurvePointDto> curvePointDtoList = curvePointService.findAll();

        model.addAttribute("curvePointDtoList", curvePointDtoList);
        model.addAttribute("user", "USER TESTA");

        return "curvePoint/list";
    }

    @GetMapping("/CurvePoint/add")
    public String addCurvePointPage(CurvePointDto curvePointDto) {

        return "curvePoint/add";

    }

    @PostMapping("/CurvePoint/add")
    public String addCurvePoint(@Validated CurvePointDto curvePointDto,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "curvePoint/add";
        }

        curvePointService.create(curvePointDto);

        return "redirect:/CurvePoint";
    }

    @GetMapping(value = "/CurvePoint/update/{id}")
    public String updatePage(
            @PathVariable(value = "id") int curePointId,
            Model model) {
        CurvePointDto curvePointDto = curvePointService.findById(curePointId);

        model.addAttribute("curvePointDto", curvePointDto);

        return "curvePoint/update";
    }

    @PostMapping(value = "/CurvePoint/update/{id}")
    public String update(
            @PathVariable(value = "id") int curePointId,
            @Validated CurvePointDto curvePointDto,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "/curvePoint/update";
        }
        curvePointService.update(curvePointDto, curePointId);
        return "redirect:/CurvePoint";

    }


    @GetMapping(value = "/CurvePoint/delete/{id}")
    public String delete(
            @PathVariable(value = "id") int curvePointId
    ) {

        curvePointService.delete(curvePointId);

        return "redirect:/CurvePoint";
    }
}
