package ru.afso.projectzero.services;

import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.turf.TurfJoins;

import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.afso.projectzero.dto.FilledSurveyDTO;
import ru.afso.projectzero.dto.QuestionDTO;
import ru.afso.projectzero.dto.SurveyDTO;
import ru.afso.projectzero.entities.FilledSurveyEntity;
import ru.afso.projectzero.entities.QuestionEntity;
import ru.afso.projectzero.entities.AnswerEntity;
import ru.afso.projectzero.entities.SurveyEntity;
import ru.afso.projectzero.models.FilledSurveyModel;
import ru.afso.projectzero.models.QuestionModel;
import ru.afso.projectzero.models.AnswerModel;
import ru.afso.projectzero.models.SurveyModel;
import ru.afso.projectzero.repositories.FilledSurveyRepository;
import ru.afso.projectzero.repositories.QuestionRepository;
import ru.afso.projectzero.repositories.SurveyRepository;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final FilledSurveyRepository filledSurveyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository,
                         QuestionRepository questionRepository,
                         FilledSurveyRepository filledSurveyRepository, 
                         ModelMapper modelMapper) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.filledSurveyRepository = filledSurveyRepository;
        this.modelMapper = modelMapper;
        
        Converter<List<QuestionEntity>, List<QuestionModel>> questionConverter = 
        		ctx -> ctx.getSource() == null ? null : ctx.getSource()
        		.stream().map(question -> modelMapper.map(question,  QuestionModel.class))
        		.collect(Collectors.toList());
        this.modelMapper.typeMap(SurveyEntity.class, SurveyModel.class)
				.addMappings(mapper -> mapper.using(questionConverter)
				.map(SurveyEntity::getQuestions, SurveyModel::setQuestions));
        
        Converter<List<AnswerEntity>, List<AnswerModel>> answerConverter = 
        		ctx -> ctx.getSource() == null ? null : ctx.getSource()
        		.stream().map(answer -> modelMapper.map(answer, AnswerModel.class))
        		.collect(Collectors.toList());
        this.modelMapper.typeMap(QuestionEntity.class, QuestionModel.class)
        		.addMappings(mapper -> mapper.using(answerConverter)
        		.map(QuestionEntity::getAnswers, QuestionModel::setAnswers));
        
        Converter<SurveyDTO, SurveyEntity> surveyDTOConverter = ctx -> {
        	SurveyDTO source = ctx.getSource();
        	final SurveyEntity destination = 
        			ctx.getDestination() == null ? new SurveyEntity() : ctx.getDestination();
        	destination.setBeginDate(source.getBeginDate());
        	destination.setEndDate(source.getEndDate());
        	destination.setTitle(source.getTitle());
        	destination.setDescription(source.getDescription());
        	destination.setArea(FeatureCollection.fromJson(source.getArea().toString()));
        	if (source.getQuestions() != null) {
        		destination.setQuestions(source.getQuestions().stream()
	            		.map(QuestionEntity::fromDTO)
	            		.map(question -> {
	            			question.setSurvey(destination);
	            			return question;
	            			}).collect(Collectors.toList()));
        	} else {
        		destination.setQuestions(Collections.<QuestionEntity>emptyList());
        	}
        	return destination;
        };
        this.modelMapper.typeMap(SurveyDTO.class, SurveyEntity.class)
        		.setConverter(surveyDTOConverter);
    }

    public List<SurveyModel> getSurveys(int offset, int count) {
        return StreamSupport.stream(surveyRepository.findAll().spliterator(), false)
        		.map(entity -> modelMapper.map(entity, SurveyModel.class))
                .skip(offset).limit(count).collect(Collectors.toList());
    }

    public List<SurveyModel> getSurveysByLocation(double latitude, double longitude) {
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
                }).map(entity -> modelMapper.map(entity, SurveyModel.class)).collect(Collectors.toList());
    }

    public SurveyModel getSurveyByIdAndLocation(long id, double latitude, double longitude) {
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
            	return modelMapper.map(entity, SurveyModel.class);
            }
        }
        throw new NoSuchElementException();
    }

    public SurveyModel getSurveyById(long id) {
        return modelMapper.map(surveyRepository.findById(id).orElseThrow(NoSuchElementException::new), SurveyModel.class);
    }

    public SurveyModel createSurvey(SurveyDTO surveyDTO) {
    	SurveyEntity survey = modelMapper.map(surveyDTO, SurveyEntity.class);
        return modelMapper.map(surveyRepository.save(survey), SurveyModel.class);
    }

    public SurveyModel updateSurvey(long id, SurveyDTO surveyDTO) {
    	SurveyEntity survey = surveyRepository.findById(id).orElseThrow(NoSuchElementException::new);
        modelMapper.map(surveyDTO, survey);
        return modelMapper.map(surveyRepository.save(survey), SurveyModel.class);
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

    public SurveyModel addQuestion(long surveyId, QuestionDTO questionDTO) {
        SurveyEntity survey = surveyRepository.findById(surveyId).orElseThrow(NoSuchElementException::new);
        survey.addQuestion(new QuestionEntity(questionDTO));
        return modelMapper.map(surveyRepository.save(survey), SurveyModel.class);
    }

    public void deleteQuestionById(long questionId) {
        questionRepository.deleteById(questionId);
    }

    public List<FilledSurveyModel> getFilledSurveys(int offset, int count) {
        return StreamSupport.stream(filledSurveyRepository.findAll().spliterator(), false)
        		.map(FilledSurveyEntity::toModel)
                .skip(offset).limit(count).collect(Collectors.toList());
    }

    public FilledSurveyModel getFilledSurveyById(long id) {
        return filledSurveyRepository.findById(id).orElseThrow(NoSuchElementException::new).toModel();
    }

    public FilledSurveyModel createFilledSurvey(FilledSurveyDTO filledSurveyDTO) {
    	FilledSurveyEntity filledSurvey = new FilledSurveyEntity(filledSurveyDTO);
        return filledSurveyRepository.save(filledSurvey).toModel();
    }
}
