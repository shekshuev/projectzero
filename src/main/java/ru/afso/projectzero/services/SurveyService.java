package ru.afso.projectzero.services;

import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.turf.TurfJoins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.afso.projectzero.dto.SurveyDTO;
import ru.afso.projectzero.entities.FilledSurveyEntity;
import ru.afso.projectzero.entities.QuestionEntity;
import ru.afso.projectzero.entities.SurveyEntity;
import ru.afso.projectzero.repositories.FilledSurveyRepository;
import ru.afso.projectzero.repositories.QuestionRepository;
import ru.afso.projectzero.repositories.SurveyRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final FilledSurveyRepository filledSurveyRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository,
                         QuestionRepository questionRepository,
                         FilledSurveyRepository filledSurveyRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.filledSurveyRepository = filledSurveyRepository;
    }

    public List<SurveyEntity> getSurveys(int offset, int count) {
        return StreamSupport.stream(surveyRepository.findAll().spliterator(), false)
                .skip(offset).limit(count).collect(Collectors.toList());
    }

    public List<SurveyEntity> getSurveysByLocation(double latitude, double longitude) {
        Point p = Point.fromLngLat(longitude, latitude);
        return StreamSupport.stream(surveyRepository.findAll().spliterator(), false)
                .filter(s -> {
                    if (s.getArea().features() != null) {
                        return s.getArea().features().stream().anyMatch(f -> {
                            if (f.geometry() != null) {
                                return TurfJoins.inside(p, (Polygon) f.geometry());
                            } else {
                                return false;
                            }
                        });
                    } else {
                        return false;
                    }
                }).collect(Collectors.toList());
    }

    public SurveyEntity getSurveyByIdAndLocation(long id, double latitude, double longitude) {
        Point p = Point.fromLngLat(longitude, latitude);
        SurveyEntity entity = surveyRepository.findById(id).orElseThrow(NoSuchElementException::new);
        if (entity.getArea().features() != null) {
            boolean flag = entity.getArea().features().stream().anyMatch(f -> {
                if (f.geometry() != null) {
                    return TurfJoins.inside(p, (Polygon) f.geometry());
                } else {
                    return false;
                }
            });
            if (flag) {
                return entity;
            }
        }
        throw new NoSuchElementException();
    }

    public SurveyEntity getSurveyById(long id) {
        return surveyRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public SurveyEntity createSurvey(SurveyEntity survey) {
        surveyRepository.save(survey);
        return survey;
    }

    public SurveyEntity updateSurvey(SurveyEntity survey, SurveyDTO surveyDTO) {
        survey.setTitle(surveyDTO.getTitle());
        survey.setDescription(surveyDTO.getDescription());
        survey.setBeginDate(surveyDTO.getBeginDate());
        survey.setEndDate(surveyDTO.getEndDate());
        return surveyRepository.save(survey);
    }

    public void deleteSurveyById(long id) {
        surveyRepository.deleteById(id);
    }

    public long getTotalSurveysCount() {
        return surveyRepository.count();
    }

    public long getTotalFilledSurveysCount() {
        return filledSurveyRepository.count();
    }

    public SurveyEntity addQuestion(SurveyEntity survey, QuestionEntity question) {
        question.setSurvey(survey);
        survey.addQuestion(question);
        return surveyRepository.save(survey);
    }

    public void deleteQuestionById(long questionId) {
        questionRepository.deleteById(questionId);
    }

    public List<FilledSurveyEntity> getFilledSurveys(int offset, int count) {
        return StreamSupport.stream(filledSurveyRepository.findAll().spliterator(), false)
                .skip(offset).limit(count).collect(Collectors.toList());
    }

    public FilledSurveyEntity getFilledSurveyById(long id) {
        return filledSurveyRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public FilledSurveyEntity createFilledSurvey(FilledSurveyEntity filledSurvey) {
        filledSurveyRepository.save(filledSurvey);
        return filledSurvey;
    }
}
