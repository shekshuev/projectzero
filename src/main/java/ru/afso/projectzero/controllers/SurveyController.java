package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.entities.ResearchEntity;
import ru.afso.projectzero.entities.SurveyEntity;
import ru.afso.projectzero.models.NewSurveyModel;
import ru.afso.projectzero.models.SurveyModel;
import ru.afso.projectzero.services.SurveyService;
import ru.afso.projectzero.utils.ApiResponse;
import ru.afso.projectzero.utils.ErrorResponse;
import ru.afso.projectzero.utils.SuccessResponse;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0/survey")
public class SurveyController {

    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping
    public ApiResponse<HashMap<String, Object>> getSurveys(
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", surveyService.getTotalSurveysCount());
        map.put("surveys", surveyService.getSurveys(offset, count).stream().map(SurveyEntity::toModel).collect(Collectors.toList()));
        return new SuccessResponse<>(map);
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getSurvey(@PathVariable long id) {
        SurveyEntity survey = surveyService.getSurveyById(id);
        if (survey != null) {
            return new SuccessResponse<>(survey.toModel());
        } else {
            return new ErrorResponse<>(null);
        }
    }

    @PostMapping(consumes = { "application/json" })
    public ApiResponse<?> createSurvey(@RequestBody NewSurveyModel newSurveyModel) {
        try {
            return new SuccessResponse<>(surveyService.createSurvey(newSurveyModel.toEntity()).toModel());
        } catch (DataAccessException e) {
            return new ErrorResponse<>(e.getMessage());
        }
    }

//    @PutMapping(value = "/{id}", consumes = { "application/json" })
//    public ApiResponse<?> updateSurvey(@RequestBody NewSurveyModel newSurveyModel, @PathVariable long id) {
//        SurveyEntity survey = newSurveyModel.toEntity();
//        survey.setId(id);
//        try {
//            return new SuccessResponse<>(surveyService.updateSurvey(survey).toModel());
//        } catch (DataAccessException e) {
//            return new ErrorResponse<>(e.getMessage());
//        }
//    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteSurvey(@PathVariable long id) {
        surveyService.deleteSurveyById(id);
        return new SuccessResponse<>(true);
    }
}
