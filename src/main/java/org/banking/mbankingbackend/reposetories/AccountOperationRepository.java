package org.banking.mbankingbackend.reposetories;

import org.banking.mbankingbackend.entities.AccountOperation;
import org.banking.mbankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    
}
