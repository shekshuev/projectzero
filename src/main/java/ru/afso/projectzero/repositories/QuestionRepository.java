package ru.afso.projectzero.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.afso.projectzero.entities.QuestionEntity;

@Repository
public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {

}
