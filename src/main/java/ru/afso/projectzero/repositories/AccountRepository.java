package ru.afso.projectzero.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.afso.projectzero.models.Account;

public interface AccountRepository extends MongoRepository<Account, String> {

}
