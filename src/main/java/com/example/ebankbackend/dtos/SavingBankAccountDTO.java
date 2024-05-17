package com.example.ebankbackend.dtos;


import com.example.ebankbackend.enumes.AccountStatus;

import lombok.Data;


import java.util.Date;



@Data
public class SavingBankAccountDTO extends BankAccountDTO {

    private String id;
    private  double balance;
    private Date createdAT;

    private AccountStatus status;

    private customerDTO customerDTO;
     private double interestRate;



}
