package org.banking.mbankingbackend.dtos;

import lombok.Data;

@Data
public class AddRoleToUserRequest {
    String userName;
    String roleName;
}
