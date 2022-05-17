package org.banking.mbankingbackend.reposetories;

import org.banking.mbankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {
    @Query("select c from Customer c where c.nom like :kw or c.mail like :kw")
    List<Customer> searchCustomer(@Param("kw") String keyword);
}
