package org.banking.mbankingbackend.reposetories;

import org.banking.mbankingbackend.dtos.BankAccountDTO;
import org.banking.mbankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {

    @Query("select b from BankAccount b where b.customer.id=:i")
    List<BankAccount> findByCustomer_Id(@Param("i") String customerId);
}
