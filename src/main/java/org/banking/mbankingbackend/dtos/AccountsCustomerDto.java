package org.banking.mbankingbackend.dtos;

import org.banking.mbankingbackend.enums.AccountStatus;

import java.util.Date;

public class AccountsCustomerDto {
    private String type;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customer;
    private double overDraft;
    private double interestRate;
}
