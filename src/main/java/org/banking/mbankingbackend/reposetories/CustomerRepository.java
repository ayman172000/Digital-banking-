package org.banking.mbankingbackend.reposetories;

import org.banking.mbankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,String> {
}
