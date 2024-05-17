package com.example.ebankbackend.services;


import com.example.ebankbackend.entities.BankAccount;
import com.example.ebankbackend.entities.CurrentAccount;
import com.example.ebankbackend.entities.SavingAccount;
import com.example.ebankbackend.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {
    @Autowired
  private  BankAccountRepository bankAccountRepository;
    public void consulter(){
                BankAccount bankAccount=
                        bankAccountRepository.findById(  "1fe221d9-95cf-41b1-8ed0-d98d118bef80").orElse(null);
                if(bankAccount!=null) {
                    System.out.println("*******************");
                    System.out.println(bankAccount.getId());
                    System.out.println(bankAccount.getBalance());
                    System.out.println(bankAccount.getStatus());
                    System.out.println(bankAccount.getCreatedAT());
                    System.out.println(bankAccount.getCustomer().getName());
                    System.out.println(bankAccount.getClass().getSimpleName());
                    if (bankAccount instanceof CurrentAccount) {
                        System.out.println("Over Draft=>" + ((CurrentAccount) bankAccount).getOverDraft());

                    } else if (bankAccount instanceof SavingAccount) {
                        System.out.println("Rate=>" + ((SavingAccount) bankAccount).getInterstRate());
                    }
                    bankAccount.getAccountOperations().forEach(op -> {
                        System.out.println(op.getType() + "\t" + op.getAmount() + "\t" + op.getOperationDAte());

                    });
                }
    }
}
