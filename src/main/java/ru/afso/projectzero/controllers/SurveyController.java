package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.models.Survey;
import ru.afso.projectzero.repositories.SurveyRepository;
import ru.afso.projectzero.utils.ApiResponse;
import ru.afso.projectzero.utils.SuccessResponse;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0/survey")
public class SurveyController {

    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @GetMapping
    public ApiResponse<HashMap<String, Object>> getSurveys(
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", surveyRepository.count());
        map.put("surveys", surveyRepository.findAll()
                .stream().skip(offset).limit(count).collect(Collectors.toList()));
        return new SuccessResponse<>(map);
    }

    @GetMapping("/{id}")
    public ApiResponse<Survey> getSurvey(@PathVariable String id) {
        return new SuccessResponse<>(surveyRepository.findById(id).orElse(null));
    }

    @PostMapping(consumes = { "application/json" })
    public ApiResponse<Survey> createSurvey(@RequestBody Survey survey) {
        surveyRepository.save(survey);
        return new SuccessResponse<>(survey);
    }

    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ApiResponse<Survey> updateSurvey(@RequestBody Survey survey, @PathVariable String id) {
        survey.setId(id);
        surveyRepository.save(survey);
        return new SuccessResponse<>(survey);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteSurvey(@PathVariable String id) {
        surveyRepository.deleteById(id);
        return new SuccessResponse<>(true);
    }
}
