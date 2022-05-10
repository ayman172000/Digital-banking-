package org.banking.mbankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private String id;
    private String nom;
    private String mail;
    @OneToMany(mappedBy = "customer")
    private List<BankAccount> bankAccounts;
}
