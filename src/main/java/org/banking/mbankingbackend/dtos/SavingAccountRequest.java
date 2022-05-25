package org.banking.mbankingbackend.dtos;

import lombok.Data;

@Data
public class SavingAccountRequest {
    private double balance;
    private double decouvert;
    private String customerId;

}
