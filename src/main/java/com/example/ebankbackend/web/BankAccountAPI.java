package com.example.ebankbackend.web;

import com.example.ebankbackend.dtos.AccountHistoryDTO;
import com.example.ebankbackend.dtos.AccountOperationDTO;
import com.example.ebankbackend.dtos.BankAccountDTO;
import com.example.ebankbackend.exceptions.BankAccountNotFoundException;
import com.example.ebankbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountAPI {
    private BankAccountService bankAccountService;
   @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
       return bankAccountService.getBankAccount(accountId);


   }
   @GetMapping("/accounts")
   public List<BankAccountDTO> listAccounts(){
       return bankAccountService.bankAccountList();
   }
    @GetMapping("/accounts/{accountId}/pageOperation")
   public AccountHistoryDTO getAccountHistory(@PathVariable  String accountId, @RequestParam(name="page",defaultValue ="0" ) int page, @RequestParam(name="size",defaultValue ="5" ) int size ) throws BankAccountNotFoundException {
       return bankAccountService.getAccountHistory(accountId,page,size);

   }

}
