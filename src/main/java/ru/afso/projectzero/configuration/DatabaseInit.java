package ru.afso.projectzero.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.afso.projectzero.dto.AccountDTO;
import ru.afso.projectzero.entities.AccountEntity;
import ru.afso.projectzero.services.AccountService;

@Component
public class DatabaseInit implements InitializingBean {

    @Autowired
    private AccountService accountService;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (accountService.getTotalAccountsCount() == 0) {
            AccountDTO dto = new AccountDTO();
            dto.setFirstName("John");
            dto.setLastName("Doe");
            dto.setMiddleName("J.");
            dto.setUserName("admin");
            dto.setPassword("admin");
            dto.setRole("admin");
            accountService.createAccount(new AccountEntity(dto));
            dto.setFirstName("Jane");
            dto.setLastName("Doe");
            dto.setMiddleName("J.");
            dto.setUserName("interviewer");
            dto.setPassword("interviewer");
            dto.setRole("interviewer");
            accountService.createAccount(new AccountEntity(dto));
        }
    }
}
