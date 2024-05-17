package com.example.ebankbackend.web;

import com.example.ebankbackend.dtos.customerDTO;
import com.example.ebankbackend.entities.customer;
import com.example.ebankbackend.exceptions.CustomerNotFoundException;
import com.example.ebankbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j

public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<customerDTO> customers(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("/customers/{id}")
    public  customerDTO getCustomer(@PathVariable(name="id") Long customerId) throws CustomerNotFoundException {
    return bankAccountService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    public  customerDTO saveCustomer(@RequestBody customerDTO customerDTO){
return  bankAccountService.saveCustomer(customerDTO);
    }
    @PutMapping("/customers/{customerId}")
    public  customerDTO updateCustomer(@PathVariable long customerId,@RequestBody customerDTO customerDTO){
        customerDTO.setId(customerId);
       return bankAccountService.updateCustomer(customerDTO);

    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(long id){
     bankAccountService.deleteCustomer(id);
    }
}
