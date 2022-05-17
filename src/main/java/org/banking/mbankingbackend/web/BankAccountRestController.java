package org.banking.mbankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.banking.mbankingbackend.dtos.*;
import org.banking.mbankingbackend.entities.CurrentAccount;
import org.banking.mbankingbackend.services.BankAccountService;
import org.banking.mbankingbackend.services.exception.BankAccountNotFoundException;
import org.banking.mbankingbackend.services.exception.CustomerNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class BankAccountRestController {
    BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable(name = "accountId") String AccountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(AccountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts()
    {
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId)
    {
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name = "page",defaultValue = "0") int page,
                                               @RequestParam(name = "page",defaultValue = "5")int size) throws BankAccountNotFoundException {
       return bankAccountService.getAccountHistory(accountId,page,size);
    }

    /*@PostMapping("/accounts")
    public BankAccountDTO saveCurrentAccount(@RequestBody double initialBalance,@RequestBody double decouvert,@RequestBody String customerId ) throws CustomerNotFoundException {
        //saveCurrentBankAccount(double initialBalance, double decouvert, String customerId)
        return bankAccountService.saveCurrentBankAccount(initialBalance,decouvert,customerId);

    }*/
    @PostMapping("/accounts")
    public BankAccountDTO saveSavingAccount(@RequestBody double initialBalance,@RequestBody double interestRate,@RequestBody String customerId) throws CustomerNotFoundException {
        //saveSavingBankAccount(double initialBalance, double interestRate, String customerId)
        return bankAccountService.saveSavingBankAccount(initialBalance,interestRate,customerId);
    }
}
