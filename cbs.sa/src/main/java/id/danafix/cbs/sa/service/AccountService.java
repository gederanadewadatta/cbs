package id.danafix.cbs.sa.service;


import id.danafix.cbs.sa.entity.PrimaryAccount;
import id.danafix.cbs.sa.entity.SavingsAccount;

import java.security.Principal;

public interface AccountService {

    PrimaryAccount createPrimaryAccount();

    SavingsAccount createSavingsAccount();

    void deposit(String accountType, double amount, Principal principal);

    void withdraw(String accountType, double amount, Principal principal);

}