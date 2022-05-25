package org.banking.mbankingbackend.dtos;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class CurrentAccountRequest {
    private double initialBalance;
    private double decouvert;
    private String customerId;
}
