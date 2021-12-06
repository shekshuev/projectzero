package ru.afso.projectzero.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.afso.projectzero.entities.ResearchEntity;

public interface ResearchRepository extends MongoRepository<ResearchEntity, String> {

}
