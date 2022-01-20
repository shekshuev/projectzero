package ru.afso.projectzero.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.afso.projectzero.entities.AccountEntity;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByUserName(String userName);

}
