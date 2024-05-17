package com.example.ebankbackend.entities;

import com.example.ebankbackend.enumes.OperaTionType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDAte;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperaTionType type;
    @ManyToOne
    private BankAccount bankAccount;
    private  String description;
}
