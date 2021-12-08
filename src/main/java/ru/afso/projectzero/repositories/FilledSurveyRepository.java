package ru.afso.projectzero.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.afso.projectzero.entities.FilledSurveyEntity;

@Repository
public interface FilledSurveyRepository extends CrudRepository<FilledSurveyEntity, Long> {
}
