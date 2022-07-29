package ru.afso.projectzero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.afso.projectzero.dto.AccountDTO;
import ru.afso.projectzero.entities.AccountEntity;
import ru.afso.projectzero.models.AccountModel;
import ru.afso.projectzero.repositories.AccountRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountModel> getAccounts(int offset, int count) {
        return StreamSupport.stream(accountRepository.findAll().spliterator(), false)
                .skip(offset).limit(count).map(AccountEntity::toModel).collect(Collectors.toList());
    }

    public AccountModel getAccountByUsername(String username) {
        return accountRepository.findByUserName(username).orElseThrow(NoSuchElementException::new).toModel();
    }
    
    public AccountEntity getAccountEntityByUsername(String username) {
    	return accountRepository.findByUserName(username).orElseThrow(NoSuchElementException::new);
    }

    public AccountModel getAccountById(long id) {
        return accountRepository.findById(id).orElseThrow(NoSuchElementException::new).toModel();
    }

    public AccountModel createAccount(AccountDTO accountDTO) {
    	AccountEntity account = new AccountEntity(accountDTO);
        return accountRepository.save(account).toModel();
    }

    public AccountModel updateAccount(long id, AccountDTO accountDTO) {
    	AccountEntity account = new AccountEntity(accountDTO);
    	account.setId(id);
        return accountRepository.save(account).toModel();
    }

    public void deleteAccountById(long id) {
        accountRepository.deleteById(id);
    }

    public long getTotalAccountsCount() {
        return accountRepository.count();
    }

}
