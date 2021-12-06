package ru.afso.projectzero.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.afso.projectzero.entities.SurveyEntity;

public interface SurveyRepository extends MongoRepository<SurveyEntity, String> {

}
