package ru.afso.projectzero.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.afso.projectzero.entities.AccountEntity;

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

}
