package ru.afso.projectzero.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.afso.projectzero.entities.AccountEntity;

public interface AccountRepository extends MongoRepository<AccountEntity, String> {

}
