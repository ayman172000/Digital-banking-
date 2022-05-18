package org.banking.mbankingbackend;

import org.banking.mbankingbackend.dtos.BankAccountDTO;
import org.banking.mbankingbackend.dtos.CurrentBankAccountDTO;
import org.banking.mbankingbackend.dtos.CustomerDTO;
import org.banking.mbankingbackend.dtos.SavingBankAccountDTO;
import org.banking.mbankingbackend.entities.Customer;
import org.banking.mbankingbackend.services.BankAccountServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class MbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountServiceImpl bankAccountService)
    {
        return args -> {
            /*
            CustomerDTO customerDTO=new CustomerDTO();
            //customerDTO.setId("1");
            customerDTO.setMail("ayman@gmail.com");
            customerDTO.setNom("ayman");
            bankAccountService.saveCustomer(customerDTO);
            CustomerDTO customerDTO1=new CustomerDTO();
            //customerDTO1.setId("2");
            customerDTO1.setMail("jermoumi@gmail.com");
            customerDTO1.setNom("mouad");
            bankAccountService.saveCustomer(customerDTO1);
            //bankAccountService.saveCurrentBankAccount(1000l,10,)*/

            bankAccountService.saveCurrentBankAccount(1000,10,"25d48d82-3399-4b8b-8d8b-af7e37a15c63");
            bankAccountService.saveSavingBankAccount(2000,50,"ce191d7f-614d-46c3-9597-60780b70a663");
        };
    }

}
