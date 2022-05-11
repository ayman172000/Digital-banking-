package org.banking.mbankingbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.banking.mbankingbackend.dtos.CustomerDTO;
import org.banking.mbankingbackend.entities.*;
import org.banking.mbankingbackend.enums.OperationType;
import org.banking.mbankingbackend.mappers.BankAccountMapperImpl;
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
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl DTOMapper;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("saving new Customer");
        Customer customer=DTOMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer= customerRepository.save(customer);
        return DTOMapper.fromCustomer(savedCustomer);
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
    public List<CustomerDTO> listCustomers() {

        List<Customer> customers=customerRepository.findAll();
        /*List<CustomerDTO> customerDTOS=new ArrayList<>();
        for(Customer customer:customers)
        {
            CustomerDTO customerDTO=DTOMapper.fromCustomer(customer);
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;*/
        return customers.stream().map(cust->DTOMapper.fromCustomer(cust)).collect(Collectors.toList());
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

    @Override
    public CustomerDTO getCustomer(String customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("customer not found");
        return DTOMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("saving new Customer");
        Customer customer=DTOMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer= customerRepository.save(customer);
        return DTOMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(String customerId)
    {
        customerRepository.deleteById(customerId);
    }
}
