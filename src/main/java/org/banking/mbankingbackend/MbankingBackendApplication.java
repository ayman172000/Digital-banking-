package org.banking.mbankingbackend;

import org.banking.mbankingbackend.dtos.BankAccountDTO;
import org.banking.mbankingbackend.dtos.CurrentBankAccountDTO;
import org.banking.mbankingbackend.dtos.CustomerDTO;
import org.banking.mbankingbackend.dtos.SavingBankAccountDTO;
import org.banking.mbankingbackend.entities.Customer;
import org.banking.mbankingbackend.reposetories.BankAccountRepository;
import org.banking.mbankingbackend.services.BankAccountServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class MbankingBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(MbankingBackendApplication.class, args);

    }

    //@Bean
    CommandLineRunner commandLineRunner(BankAccountServiceImpl bankAccountService, BankAccountRepository bankAccountRepository)
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

            //bankAccountService.saveCurrentBankAccount(1000,10,"25d48d82-3399-4b8b-8d8b-af7e37a15c63");
            //bankAccountService.saveSavingBankAccount(2000,50,"ce191d7f-614d-46c3-9597-60780b70a663");
            bankAccountService.credit(
                    "8e9f3bea-03c0-4126-b0f9-eede4baaf0e3",1000,"credit");
            bankAccountService.debit(
                    "8e9f3bea-03c0-4126-b0f9-eede4baaf0e3",1000,"debit");

            bankAccountService.transfer("8e9f3bea-03c0-4126-b0f9-eede4baaf0e3","85a86abd-3ff2-44d9-b801-c7851682071a",100,"trasfer");bankAccountService.credit(
                    "8e9f3bea-03c0-4126-b0f9-eede4baaf0e3",1000,"credit");
            bankAccountService.debit(
                    "8e9f3bea-03c0-4126-b0f9-eede4baaf0e3",1000,"debit");

            bankAccountService.transfer("8e9f3bea-03c0-4126-b0f9-eede4baaf0e3","85a86abd-3ff2-44d9-b801-c7851682071a",100,"trasfer");bankAccountService.credit(
                    "8e9f3bea-03c0-4126-b0f9-eede4baaf0e3",1000,"credit");
            bankAccountService.debit(
                    "8e9f3bea-03c0-4126-b0f9-eede4baaf0e3",1000,"debit");

            bankAccountService.transfer("8e9f3bea-03c0-4126-b0f9-eede4baaf0e3","85a86abd-3ff2-44d9-b801-c7851682071a",100,"trasfer");bankAccountService.credit(
                    "8e9f3bea-03c0-4126-b0f9-eede4baaf0e3",1000,"credit");
            bankAccountService.debit(
                    "8e9f3bea-03c0-4126-b0f9-eede4baaf0e3",1000,"debit");

            bankAccountService.transfer("8e9f3bea-03c0-4126-b0f9-eede4baaf0e3","85a86abd-3ff2-44d9-b801-c7851682071a",100,"trasfer");bankAccountService.credit(
                    "8e9f3bea-03c0-4126-b0f9-eede4baaf0e3",1000,"credit");
            bankAccountService.debit(
                    "8e9f3bea-03c0-4126-b0f9-eede4baaf0e3",1000,"debit");

            bankAccountService.transfer("8e9f3bea-03c0-4126-b0f9-eede4baaf0e3","85a86abd-3ff2-44d9-b801-c7851682071a",100,"trasfer");

            //System.out.println(bankAccountService.getAccountsOfCustomer("ce191d7f-614d-46c3-9597-60780b70a663"));
        };
    }
    //@Bean
    CommandLineRunner commandLineRunner(BankAccountServiceImpl bankAccountService)
    {
        return args -> {
            List<BankAccountDTO> accountsOfCustomer = bankAccountService.getAccountsOfCustomer("25d48d82-3399-4b8b-8d8b-af7e37a15c63");
            System.out.println(accountsOfCustomer);
        };
    }


}
