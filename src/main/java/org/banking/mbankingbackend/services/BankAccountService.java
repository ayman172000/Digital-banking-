package org.banking.mbankingbackend.services;

import org.banking.mbankingbackend.entities.BankAccount;
import org.banking.mbankingbackend.entities.Customer;
import org.banking.mbankingbackend.services.exception.BalanceNotSufficentException;
import org.banking.mbankingbackend.services.exception.BankAccountNotFoundException;
import org.banking.mbankingbackend.services.exception.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    BankAccount saveBankCurrentAccount(double initialBalance,double decouvert,String customerId) throws CustomerNotFoundException;
    BankAccount saveBankSavingAccount(double initialBalance,double interestRate,String customerId) throws CustomerNotFoundException;
    List<Customer> listCustomers();
    BankAccount getBankAccout(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void credit(String accountId,double amount,String description) throws BalanceNotSufficentException, BankAccountNotFoundException;
    void transfer(String accountIdSrc,String accountIdDest,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficentException;
    List<BankAccount> bankAccountList();

}
