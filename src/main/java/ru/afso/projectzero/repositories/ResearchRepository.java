package ru.afso.projectzero.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.afso.projectzero.models.Research;

public interface ResearchRepository extends MongoRepository<Research, String> {

}
