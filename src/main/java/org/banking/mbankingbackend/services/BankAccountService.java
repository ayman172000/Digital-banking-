package org.banking.mbankingbackend.services;

import org.banking.mbankingbackend.dtos.*;
import org.banking.mbankingbackend.entities.BankAccount;
import org.banking.mbankingbackend.entities.Customer;
import org.banking.mbankingbackend.services.exception.BalanceNotSufficentException;
import org.banking.mbankingbackend.services.exception.BankAccountNotFoundException;
import org.banking.mbankingbackend.services.exception.CustomerNotFoundException;
import org.banking.mbankingbackend.services.exception.TransferException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double decouvert, String customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, String customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void credit(String accountId,double amount,String description) throws BalanceNotSufficentException, BankAccountNotFoundException;
    void transfer(String accountIdSrc,String accountIdDest,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficentException, TransferException;
    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(String customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(String customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId,int page,int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);

    List<BankAccountDTO> getAccountsOfCustomer(String CustomerId) throws CustomerNotFoundException;
}
