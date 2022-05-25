package org.banking.mbankingbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.banking.mbankingbackend.dtos.*;
import org.banking.mbankingbackend.entities.*;
import org.banking.mbankingbackend.enums.AccountStatus;
import org.banking.mbankingbackend.enums.OperationType;
import org.banking.mbankingbackend.mappers.BankAccountMapperImpl;
import org.banking.mbankingbackend.reposetories.AccountOperationRepository;
import org.banking.mbankingbackend.reposetories.BankAccountRepository;
import org.banking.mbankingbackend.reposetories.CustomerRepository;
import org.banking.mbankingbackend.services.exception.BalanceNotSufficentException;
import org.banking.mbankingbackend.services.exception.BankAccountNotFoundException;
import org.banking.mbankingbackend.services.exception.CustomerNotFoundException;
import org.banking.mbankingbackend.services.exception.TransferException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        customerDTO.setId(UUID.randomUUID().toString());
        Customer customer=DTOMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer= customerRepository.save(customer);
        return DTOMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double decouvert, String customerId)
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
        CurrentAccount savedAccount =bankAccountRepository.save(bankAccount);
        return DTOMapper.fromCurrentBankAccount(savedAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, String customerId) throws CustomerNotFoundException {
        log.info("saving new saving account");
        Customer customer= customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("customer not found");
        SavingAccount bankAccount=new SavingAccount();
        bankAccount.setStatus(AccountStatus.ACTIVITED);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setInterestRate(interestRate);
        SavingAccount savedAccount =bankAccountRepository.save(bankAccount);
        return DTOMapper.fromSavingBankAccount(savedAccount);
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
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException
    {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount== null)
            throw new BankAccountNotFoundException("Account not found");
        if(bankAccount instanceof SavingAccount)
        {
            SavingAccount savingAccount= (SavingAccount) bankAccount;
            return DTOMapper.fromSavingBankAccount(savingAccount);
        }

        else if(bankAccount instanceof CurrentAccount)
        {
            CurrentAccount currentAccount= (CurrentAccount) bankAccount;
            return DTOMapper.fromCurrentBankAccount(currentAccount);
        }
        return null;
    }

    @Override
    public void debit(String accountId, double amount, String description)
            throws BankAccountNotFoundException, BalanceNotSufficentException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount== null)
            throw new BankAccountNotFoundException("Account not found");
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
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount== null)
            throw new BankAccountNotFoundException("Account not found");
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
    public void transfer(String accountIdSrc, String accountIdDest, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException, TransferException {
        BankAccount bankAccountSrc=bankAccountRepository.findById(accountIdSrc).orElse(null);
        if(bankAccountSrc== null)
            throw new BankAccountNotFoundException("Account not found");
        BankAccount bankAccountDest=bankAccountRepository.findById(accountIdDest).orElse(null);
        if(bankAccountDest== null)
            throw new BankAccountNotFoundException("Account not found");
        if(accountIdDest.equals(accountIdSrc))
            throw new TransferException("src like dest");
        debit(accountIdSrc,amount,description);
        credit(accountIdDest,amount,description);
    }

    @Override
    public List<BankAccountDTO> bankAccountList()
    {
        List<BankAccountDTO> bankAccountDTOS=bankAccountRepository.findAll().stream().map(acc->{
            if(acc instanceof SavingAccount){
                SavingAccount savingAccount= (SavingAccount) acc;
                return DTOMapper.fromSavingBankAccount(savingAccount);
            }
            else
            {
                CurrentAccount currentAccount= (CurrentAccount) acc;
                return DTOMapper.fromCurrentBankAccount(currentAccount);
            }

        }).collect(Collectors.toList());
        return bankAccountDTOS;
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
    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
       List<AccountOperation>operationList =accountOperationRepository.findByBankAccount_Id(accountId);
       return operationList.stream().map(op->DTOMapper.fromAccountOperation(op)).collect(Collectors.toList());

    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null)
            throw new BankAccountNotFoundException("account not found");
        Page<AccountOperation> accountOperations= accountOperationRepository.findByBankAccount_IdOrderByOperationDateDesc(accountId, PageRequest.of(page,size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS= accountOperations.getContent().stream().map(op->DTOMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers=customerRepository.searchCustomer(keyword);
        return customers.stream().map(cust->
                DTOMapper.fromCustomer(cust)).collect(Collectors.toList());
    }


    @Override
    public List<BankAccountDTO> getAccountsOfCustomer(String CustomerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(CustomerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        List<BankAccount> bankAccount=bankAccountRepository.findByCustomer_Id(CustomerId);
        List<BankAccountDTO> collect = bankAccount.stream().map(acc -> {
            if (acc instanceof CurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) acc;
                return DTOMapper.fromCurrentBankAccount(currentAccount);
            } else {
                SavingAccount savingAccount = (SavingAccount) acc;
                return DTOMapper.fromSavingBankAccount(savingAccount);
            }
        }).collect(Collectors.toList());
        return collect;
    }
}
