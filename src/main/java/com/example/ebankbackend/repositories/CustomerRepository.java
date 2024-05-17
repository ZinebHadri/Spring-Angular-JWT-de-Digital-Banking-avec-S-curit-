package com.example.ebankbackend.repositories;

import com.example.ebankbackend.entities.customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<customer,Long> {
}
