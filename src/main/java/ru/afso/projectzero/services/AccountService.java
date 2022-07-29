package ru.afso.projectzero.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.afso.projectzero.dto.AccountDTO;
import ru.afso.projectzero.entities.AccountEntity;
import ru.afso.projectzero.models.AccountModel;
import ru.afso.projectzero.repositories.AccountRepository;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, 
    		ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        Converter<String, String> passwordToHash = ctx -> ctx.getSource() == null ? null : new BCryptPasswordEncoder().encode(ctx.getSource());
        this.modelMapper.typeMap(AccountDTO.class, AccountEntity.class)
        	.addMappings(mapper -> mapper.using(passwordToHash).map(AccountDTO::getPassword, AccountEntity::setPasswordHash));
    }

    public List<AccountModel> getAccounts(int offset, int count) {
        return StreamSupport.stream(accountRepository.findAll().spliterator(), false)
                .skip(offset).limit(count).map(entity -> modelMapper.map(entity, AccountModel.class)).collect(Collectors.toList());
    }

    public AccountModel getAccountByUsername(String username) {
        return modelMapper.map(accountRepository.findByUserName(username).orElseThrow(NoSuchElementException::new), AccountModel.class);
    }
    
    public AccountEntity getAccountEntityByUsername(String username) {
    	return accountRepository.findByUserName(username).orElseThrow(NoSuchElementException::new);
    }

    public AccountModel getAccountById(long id) {
    	return modelMapper.map(accountRepository.findById(id).orElseThrow(NoSuchElementException::new), AccountModel.class);
    }

    public AccountModel createAccount(AccountDTO accountDTO) {
    	AccountEntity account = modelMapper.map(accountDTO, AccountEntity.class);
    	account.setCreatedAt(new Date());
        return modelMapper.map(accountRepository.save(account), AccountModel.class);
    }

    public AccountModel updateAccount(long id, AccountDTO accountDTO) {
    	AccountEntity account = modelMapper.map(accountDTO, AccountEntity.class);
    	account.setId(id);
        return modelMapper.map(accountRepository.save(account), AccountModel.class);
    }

    public void deleteAccountById(long id) {
        accountRepository.deleteById(id);
    }

    public long getTotalAccountsCount() {
        return accountRepository.count();
    }

}
