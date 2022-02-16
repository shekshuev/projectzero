package ru.afso.projectzero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.afso.projectzero.entities.AccountEntity;
import ru.afso.projectzero.models.AccountModel;
import ru.afso.projectzero.repositories.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountEntity> getAccounts(int offset, int count) {
        return StreamSupport.stream(accountRepository.findAll().spliterator(), false)
                .skip(offset).limit(count).collect(Collectors.toList());
    }

    public AccountEntity getAccountByUsername(String username) {
        return accountRepository.findByUserName(username).orElseThrow();
    }

    public AccountEntity getAccountById(long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    public AccountEntity createAccount(AccountEntity account) {
        return accountRepository.save(account);
    }

    public AccountEntity updateAccount(AccountEntity account) {
        return accountRepository.save(account);
    }

    public void deleteAccountById(long id) {
        accountRepository.deleteById(id);
    }

    public long getTotalAccountsCount() {
        return accountRepository.count();
    }

}
