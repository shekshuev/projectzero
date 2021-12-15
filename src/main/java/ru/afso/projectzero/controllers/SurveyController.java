package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.dto.FilledSurveyDTO;
import ru.afso.projectzero.dto.QuestionDTO;
import ru.afso.projectzero.dto.SurveyDTO;
import ru.afso.projectzero.entities.FilledSurveyEntity;
import ru.afso.projectzero.entities.QuestionEntity;
import ru.afso.projectzero.entities.SurveyEntity;
import ru.afso.projectzero.services.SurveyService;
import ru.afso.projectzero.utils.ApiResponse;
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
        return new SuccessResponse<>(surveyService.getSurveyById(id).toModel());
    }

    @PostMapping(consumes = { "application/json" })
    public ApiResponse<?> createSurvey(@RequestBody SurveyDTO surveyDTO) {
        return new SuccessResponse<>(surveyService.createSurvey(new SurveyEntity(surveyDTO)).toModel());
    }

    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ApiResponse<?> updateSurvey(@RequestBody SurveyDTO surveyDTO, @PathVariable long id) {
        return new SuccessResponse<>(surveyService.updateSurvey(
                surveyService.getSurveyById(id), surveyDTO).toModel());
    }

    @PostMapping(value = "/{id}/question", consumes = { "application/json" })
    public ApiResponse<?> addQuestionToSurvey(@RequestBody QuestionDTO questionDTO, @PathVariable long id) {
        return new SuccessResponse<>(surveyService.addQuestion(
                surveyService.getSurveyById(id), new QuestionEntity(questionDTO)).toModel());
    }

    @DeleteMapping("/question/{questionId}")
    public ApiResponse<?> deleteQuestionFromSurvey(@PathVariable long questionId) {
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
        return new SuccessResponse<>(surveyService.getFilledSurveyById(id).toModel());
    }

    @PostMapping(value="/filled", consumes = { "application/json" })
    public ApiResponse<?> createFilledSurvey(@Valid @RequestBody FilledSurveyDTO filledSurveyDTO) {
        return new SuccessResponse<>(surveyService.createFilledSurvey(new FilledSurveyEntity(filledSurveyDTO)).toModel());
    }
}
