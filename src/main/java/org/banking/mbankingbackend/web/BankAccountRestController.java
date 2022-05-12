package org.banking.mbankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.banking.mbankingbackend.dtos.AccountHistoryDTO;
import org.banking.mbankingbackend.dtos.AccountOperationDTO;
import org.banking.mbankingbackend.dtos.BankAccountDTO;
import org.banking.mbankingbackend.services.BankAccountService;
import org.banking.mbankingbackend.services.exception.BankAccountNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
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
}
