package com.example.ebankbackend.entities;
import com.example.ebankbackend.enumes.AccountStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import  java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE",length =4 ,discriminatorType = DiscriminatorType.STRING )
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
    @Id
    private String id;
    private  double balance;
    private Date createdAT;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private customer customer;
    @OneToMany(mappedBy = "bankAccount",fetch=FetchType.LAZY)//eager dangeureux car il charge des données qui vot pas etre utilisées
    private List<AccountOperation> accountOperations;



}
