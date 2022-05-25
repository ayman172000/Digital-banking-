package org.banking.mbankingbackend.reposetories;

import org.banking.mbankingbackend.entities.AccountOperation;
import org.banking.mbankingbackend.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    public List<AccountOperation> findByBankAccount_Id(String accountId);
    Page<AccountOperation> findByBankAccount_IdOrderByOperationDateDesc(String accountId, Pageable pageable);
}
