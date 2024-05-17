package com.example.ebankbackend.dtos;

//import jakarta.persistence.*;

import com.example.ebankbackend.entities.BankAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
public class customerDTO {
    private Long id;
    private String name;
    private String email;


}
