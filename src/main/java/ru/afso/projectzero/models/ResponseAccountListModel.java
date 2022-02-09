package ru.afso.projectzero.models;

import java.util.List;

public class ResponseAccountListModel {

    private long total;

    private List<AccountModel> accounts;


    public ResponseAccountListModel(long total, List<AccountModel> accounts) {
        this.total = total;
        this.accounts = accounts;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<AccountModel> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountModel> accounts) {
        this.accounts = accounts;
    }
}
