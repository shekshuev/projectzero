package ru.afso.projectzero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.afso.projectzero.entities.SurveyEntity;
import ru.afso.projectzero.repositories.SurveyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public List<SurveyEntity> getSurveys(int offset, int count) {
        return surveyRepository.findAll()
                .stream().skip(offset).limit(count).collect(Collectors.toList());
    }

    public SurveyEntity getSurveyById(String id) {
        return surveyRepository.findById(id).orElse(null);
    }

    public SurveyEntity createSurvey(SurveyEntity survey) {
        surveyRepository.save(survey);
        return survey;
    }

    public SurveyEntity updateSurvey(SurveyEntity survey) {
        return surveyRepository.save(survey);
    }

    public void deleteSurveyById(String id) {
        surveyRepository.deleteById(id);
    }

    public long getTotalSurveysCount() {
        return surveyRepository.count();
    }
}
