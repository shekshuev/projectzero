package ru.afso.projectzero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.afso.projectzero.entities.AccountEntity;
import ru.afso.projectzero.repositories.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountEntity> getAccounts(int offset, int count) {
        return accountRepository.findAll()
                .stream().skip(offset).limit(count).collect(Collectors.toList());
    }

    public AccountEntity getAccountById(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    public AccountEntity createAccount(AccountEntity account) {
        accountRepository.save(account);
        return account;
    }

    public AccountEntity updateAccount(AccountEntity account) {
        return accountRepository.save(account);
    }

    public void deleteAccountById(String id) {
        accountRepository.deleteById(id);
    }

    public long getTotalAccountsCount() {
        return accountRepository.count();
    }

}
