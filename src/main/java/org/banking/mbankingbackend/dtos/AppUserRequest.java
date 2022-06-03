package org.banking.mbankingbackend.dtos;

import lombok.Data;

@Data
public class AppUserRequest {
    private String username;
    private String password;
    private String rePassword;
}
