package ru.afso.projectzero.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.afso.projectzero.models.Survey;

public interface SurveyRepository extends MongoRepository<Survey, String> {

}
