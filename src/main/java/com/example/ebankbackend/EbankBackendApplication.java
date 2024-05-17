package com.example.ebankbackend;

import com.example.ebankbackend.dtos.BankAccountDTO;
import com.example.ebankbackend.dtos.CurrentBankAccountDTO;
import com.example.ebankbackend.dtos.SavingBankAccountDTO;
import com.example.ebankbackend.dtos.customerDTO;
import com.example.ebankbackend.entities.*;
import com.example.ebankbackend.enumes.AccountStatus;
import com.example.ebankbackend.enumes.OperaTionType;
import com.example.ebankbackend.exceptions.BalanceNotSufficientException;
import com.example.ebankbackend.exceptions.BankAccountNotFoundException;
import com.example.ebankbackend.exceptions.CustomerNotFoundException;
import com.example.ebankbackend.repositories.AccountOperationRepository;
import com.example.ebankbackend.repositories.BankAccountRepository;
import com.example.ebankbackend.repositories.CustomerRepository;
import com.example.ebankbackend.services.BankAccountService;
import com.example.ebankbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args->{
         Stream.of("hassan","imane","mohamed").forEach(name->{

             customerDTO customer=new customerDTO();
             customer.setName(name);
             customer.setEmail(name+"@gmail.com");

             bankAccountService.saveCustomer(customer);
         });
            bankAccountService.listCustomers().forEach(customer->{
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90009,9000,customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getId());


                } catch (CustomerNotFoundException e) {
                   e.printStackTrace();
                }

            });
            List<BankAccountDTO> bankAccounts=bankAccountService.bankAccountList();
            for(BankAccountDTO bankAccount:bankAccounts){
                for (int i=0;i<10;i++){
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId=((SavingBankAccountDTO)bankAccount).getId();
                    }else{
                        accountId=((CurrentBankAccountDTO)bankAccount).getId();
                    }

                    bankAccountService.credit(accountId,10000*Math.random()*120000, "credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"debit");
                }
            }
        };
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return  args -> {
            Stream.of("Hassan","Zineb","Aicha").forEach(name->{
                customer customer=new customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
                customerRepository.findAll().forEach(cust->{
                    CurrentAccount currentAccount=new CurrentAccount();
                    currentAccount.setId(UUID.randomUUID().toString());
                    currentAccount.setBalance(Math.random()*90000);
                    currentAccount.setCreatedAT(new Date());
                    currentAccount.setStatus(AccountStatus.CREATED);
                    currentAccount.setCustomer(cust);
                    currentAccount.setOverDraft(9000);
                    bankAccountRepository.save(currentAccount);
                    SavingAccount savingAccount=new SavingAccount();
                    savingAccount.setId(UUID.randomUUID().toString());
                    savingAccount.setBalance(Math.random()*90000);
                    savingAccount.setCreatedAT(new Date());
                    savingAccount.setStatus(AccountStatus.CREATED);
                    savingAccount.setCustomer(cust);
                    savingAccount.setInterstRate(5.5);
                    bankAccountRepository.save(savingAccount);
                });

            });
        };
    }
}
