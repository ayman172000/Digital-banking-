package org.banking.mbankingbackend.reposetories;

import org.banking.mbankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {

}
