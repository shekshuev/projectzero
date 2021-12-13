package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.entities.FilledSurveyEntity;
import ru.afso.projectzero.entities.SurveyEntity;
import ru.afso.projectzero.models.NewFilledSurveyModel;
import ru.afso.projectzero.models.NewQuestionModel;
import ru.afso.projectzero.models.NewSurveyModel;
import ru.afso.projectzero.services.SurveyService;
import ru.afso.projectzero.utils.ApiResponse;
import ru.afso.projectzero.utils.ErrorResponse;
import ru.afso.projectzero.utils.SuccessResponse;

import javax.validation.Valid;
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

    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ApiResponse<?> updateSurvey(@RequestBody NewSurveyModel newSurveyModel, @PathVariable long id) {
        SurveyEntity survey = surveyService.getSurveyById(id);
        if (survey != null) {
            try {
                return new SuccessResponse<>(surveyService.updateSurvey(survey, newSurveyModel).toModel());
            } catch (DataAccessException e) {
                return new ErrorResponse<>(e.getMessage());
            }
        } else {
            return new ErrorResponse<>("No such survey");
        }
    }

    @PostMapping(value = "/{id}/question", consumes = { "application/json" })
    public ApiResponse<?> addQuestionToSurvey(@RequestBody NewQuestionModel newQuestionModel, @PathVariable long id) {
        SurveyEntity survey = surveyService.getSurveyById(id);
        if (survey != null) {
            try {
                return new SuccessResponse<>(surveyService.addQuestion(survey, newQuestionModel.toEntity()).toModel());
            } catch (DataAccessException e) {
                return new ErrorResponse<>(e.getMessage());
            }
        } else {
            return new ErrorResponse<>("No such survey");
        }
    }

    @DeleteMapping("/{surveyId}/question/{questionId}")
    public ApiResponse<?> deleteQuestionFromSurvey(@PathVariable long surveyId, @PathVariable long questionId) {
        surveyService.deleteQuestionById(questionId);
        return new SuccessResponse<>(true);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteSurvey(@PathVariable long id) {
        surveyService.deleteSurveyById(id);
        return new SuccessResponse<>(true);
    }

    @GetMapping("/filled")
    public ApiResponse<HashMap<String, Object>> getFilledSurveys(
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", surveyService.getTotalFilledSurveysCount());
        map.put("filledSurveys", surveyService.getFilledSurveys(offset, count).stream().map(FilledSurveyEntity::toModel).collect(Collectors.toList()));
        return new SuccessResponse<>(map);
    }

    @GetMapping("/filled/{id}")
    public ApiResponse<?> getFilledSurvey(@PathVariable long id) {
        FilledSurveyEntity survey = surveyService.getFilledSurveyById(id);
        if (survey != null) {
            return new SuccessResponse<>(survey.toModel());
        } else {
            return new ErrorResponse<>(null);
        }
    }

    @PostMapping(value="/filled", consumes = { "application/json" })
    public ApiResponse<?> createFilledSurvey(@Valid @RequestBody NewFilledSurveyModel newFilledSurveyModel) {
        return new SuccessResponse<>(surveyService.createFilledSurvey(newFilledSurveyModel.toEntity()).toModel());
    }
}
