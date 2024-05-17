package com.example.ebankbackend.mappers;

import com.example.ebankbackend.dtos.AccountOperationDTO;
import com.example.ebankbackend.dtos.CurrentBankAccountDTO;
import com.example.ebankbackend.dtos.SavingBankAccountDTO;
import com.example.ebankbackend.dtos.customerDTO;
import com.example.ebankbackend.entities.AccountOperation;
import com.example.ebankbackend.entities.CurrentAccount;
import com.example.ebankbackend.entities.SavingAccount;
import com.example.ebankbackend.entities.customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMappeImpl {

    public customerDTO fromCustomer(customer customer) {
        customerDTO customerDTO = new customerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        //customerDTO.setId(customer.getId());
        //customerDTO.setName(customer.getName());
        //customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }

    public customer fromCustomerDTO(customerDTO customerDTO) {
        customer customer = new customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount) {
        SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDTO;

    }

    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingAccountDTO) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO, savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingAccountDTO.getCustomerDTO()));
        return savingAccount;

    }

    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount) {
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;
    }

    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO, currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;

    }

    public AccountOperationDTO fromaccountOperation(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;

        
        

    }




}