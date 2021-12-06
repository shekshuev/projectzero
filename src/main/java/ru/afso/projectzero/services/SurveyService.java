package ru.afso.projectzero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.afso.projectzero.entities.SurveyEntity;
import ru.afso.projectzero.repositories.SurveyRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public List<SurveyEntity> getSurveys(int offset, int count) {
        return StreamSupport.stream(surveyRepository.findAll().spliterator(), false)
                .skip(offset).limit(count).collect(Collectors.toList());
    }

    public SurveyEntity getSurveyById(long id) {
        return surveyRepository.findById(id).orElse(null);
    }

    public SurveyEntity createSurvey(SurveyEntity survey) {
        surveyRepository.save(survey);
        return survey;
    }

    public SurveyEntity updateSurvey(SurveyEntity survey) {
        //SurveyEntity old = getSurveyById(survey.getId());
        //if (survey.)
        //return surveyRepository.save(survey);
        return survey;
    }

    public void deleteSurveyById(long id) {
        surveyRepository.deleteById(id);
    }

    public long getTotalSurveysCount() {
        return surveyRepository.count();
    }
}
