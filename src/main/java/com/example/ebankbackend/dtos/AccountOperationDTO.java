package com.example.ebankbackend.dtos;

import com.example.ebankbackend.entities.BankAccount;
import com.example.ebankbackend.enumes.OperaTionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
public class AccountOperationDTO {

    private Long id;
    private Date operationDAte;
    private double amount;
    private OperaTionType type;
    private  String description;
}
