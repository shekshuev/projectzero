package ru.afso.projectzero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.afso.projectzero.entities.QuestionEntity;
import ru.afso.projectzero.entities.SurveyEntity;
import ru.afso.projectzero.models.NewSurveyModel;
import ru.afso.projectzero.repositories.QuestionRepository;
import ru.afso.projectzero.repositories.SurveyRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository, QuestionRepository questionRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
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

    public SurveyEntity updateSurvey(SurveyEntity survey, NewSurveyModel newSurveyModel) {
        survey.setTitle(newSurveyModel.getTitle());
        survey.setDescription(newSurveyModel.getDescription());
        survey.setBeginDate(newSurveyModel.getBeginDate());
        survey.setEndDate(newSurveyModel.getEndDate());
        return surveyRepository.save(survey);
    }

    public void deleteSurveyById(long id) {
        surveyRepository.deleteById(id);
    }

    public long getTotalSurveysCount() {
        return surveyRepository.count();
    }

    public SurveyEntity addQuestion(SurveyEntity survey, QuestionEntity question) {
        question.setSurvey(survey);
        survey.addQuestion(question);
        return surveyRepository.save(survey);
    }

    public void deleteQuestionById(long questionId) {
        questionRepository.deleteById(questionId);
    }
}
