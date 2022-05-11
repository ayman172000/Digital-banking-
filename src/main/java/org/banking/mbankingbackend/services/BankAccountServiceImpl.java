package org.banking.mbankingbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.banking.mbankingbackend.entities.*;
import org.banking.mbankingbackend.enums.AccountStatus;
import org.banking.mbankingbackend.enums.OperationType;
import org.banking.mbankingbackend.reposetories.AccountOperationRepository;
import org.banking.mbankingbackend.reposetories.BankAccountRepository;
import org.banking.mbankingbackend.reposetories.CustomerRepository;
import org.banking.mbankingbackend.services.exception.BalanceNotSufficentException;
import org.banking.mbankingbackend.services.exception.BankAccountNotFoundException;
import org.banking.mbankingbackend.services.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("saving new Customer");
        Customer savedCustomer= customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public BankAccount saveBankCurrentAccount(double initialBalance, double decouvert, String customerId)
            throws CustomerNotFoundException {
        log.info("saving new current account");
        Customer customer= customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("customer not found");
        CurrentAccount bankAccount=new CurrentAccount();
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setOverDraft(decouvert);
        BankAccount savedAccount =bankAccountRepository.save(bankAccount);
        return savedAccount;
    }

    @Override
    public BankAccount saveBankSavingAccount(double initialBalance, double interestRate, String customerId) throws CustomerNotFoundException {
        log.info("saving new saving account");
        Customer customer= customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("customer not found");
        SavingAccount bankAccount=new SavingAccount();
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setInterestRate(interestRate);
        BankAccount savedAccount =bankAccountRepository.save(bankAccount);
        return savedAccount;
    }

    @Override
    public List<Customer> listCustomers() {

        return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccout(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount== null)
            throw new BankAccountNotFoundException("Account not found");
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description)
            throws BankAccountNotFoundException, BalanceNotSufficentException {
        BankAccount bankAccount=getBankAccout(accountId);
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficentException("Balance Not Sufficent");
        AccountOperation operation=new AccountOperation();
        operation.setOperationDate(new Date());
        operation.setAmount(amount);
        operation.setType(OperationType.DEBIT);
        operation.setDescription("desc");
        operation.setBankAccount(bankAccount);
        accountOperationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=getBankAccout(accountId);
        AccountOperation operation=new AccountOperation();
        operation.setOperationDate(new Date());
        operation.setAmount(amount);
        operation.setType(OperationType.CREDIT);
        operation.setDescription("desc");
        operation.setBankAccount(bankAccount);
        accountOperationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSrc, String accountIdDest, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException {
        BankAccount bankAccountSrc=getBankAccout(accountIdSrc);
        BankAccount bankAccountDest=getBankAccout(accountIdDest);
        debit(accountIdSrc,amount,description);
        credit(accountIdDest,amount,description);
    }

    @Override
    public List<BankAccount> bankAccountList()
    {
        return bankAccountRepository.findAll();
    }
}
