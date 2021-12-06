package ru.afso.projectzero.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.afso.projectzero.entities.SurveyEntity;

public interface SurveyRepository extends CrudRepository<SurveyEntity, Long> {

}
