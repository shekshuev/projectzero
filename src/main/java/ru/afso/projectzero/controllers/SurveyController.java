package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.entities.SurveyEntity;
import ru.afso.projectzero.services.SurveyService;
import ru.afso.projectzero.utils.ApiResponse;
import ru.afso.projectzero.utils.ErrorResponse;
import ru.afso.projectzero.utils.SuccessResponse;

import java.util.HashMap;
import java.util.Optional;

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
        map.put("surveys", surveyService.getSurveys(offset, count));
        return new SuccessResponse<>(map);
    }

    @GetMapping("/{id}")
    public ApiResponse<SurveyEntity> getSurvey(@PathVariable String id) {
        SurveyEntity survey = surveyService.getSurveyById(id);
        if (survey != null) {
            return new SuccessResponse<>(survey);
        } else {
            return new ErrorResponse<>(null);
        }
    }

    @PostMapping(consumes = { "application/json" })
    public ApiResponse<SurveyEntity> createSurvey(@RequestBody SurveyEntity survey) {
        return new SuccessResponse<>(surveyService.createSurvey(survey));
    }

    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ApiResponse<SurveyEntity> updateSurvey(@RequestBody SurveyEntity survey, @PathVariable String id) {
        survey.setId(id);
        return new SuccessResponse<>(surveyService.updateSurvey(survey));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteSurvey(@PathVariable String id) {
        surveyService.deleteSurveyById(id);
        return new SuccessResponse<>(true);
    }
}
