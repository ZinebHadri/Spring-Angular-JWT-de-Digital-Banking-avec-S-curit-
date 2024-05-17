package com.example.ebankbackend.services;

import com.example.ebankbackend.dtos.*;
import com.example.ebankbackend.entities.*;
import com.example.ebankbackend.enumes.OperaTionType;
import com.example.ebankbackend.exceptions.BalanceNotSufficientException;
import com.example.ebankbackend.exceptions.BankAccountNotFoundException;
import com.example.ebankbackend.exceptions.CustomerNotFoundException;
import com.example.ebankbackend.mappers.BankAccountMappeImpl;
import com.example.ebankbackend.repositories.AccountOperationRepository;
import com.example.ebankbackend.repositories.BankAccountRepository;
import com.example.ebankbackend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements  BankAccountService {

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMappeImpl dtomapper;

    @Override
    public customerDTO saveCustomer(customerDTO customerDTO) {

        log.info("Saving new Customer");
        customer customer=dtomapper.fromCustomerDTO(customerDTO);
        customer savedCustomer=customerRepository.save(customer);
        return dtomapper.fromCustomer(savedCustomer);
    }


    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
       CurrentAccount currentAccount=new CurrentAccount() ;
       currentAccount.setId(UUID.randomUUID().toString());
       currentAccount.setCreatedAT(new Date());
       currentAccount.setBalance(initialBalance);
       currentAccount.setCustomer(customer);
       currentAccount.setOverDraft(overDraft);
       CurrentAccount  savedBankAccount=bankAccountRepository.save(currentAccount);

        return dtomapper.fromCurrentBankAccount(savedBankAccount);

    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {

        customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
       SavingAccount savingAccount=new SavingAccount() ;
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAT(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterstRate(interestRate);
      SavingAccount savedBankAccount=bankAccountRepository.save(savingAccount);

        return dtomapper.fromSavingBankAccount(savedBankAccount);


    }


    @Override
    public List<customerDTO> listCustomers() {

        List<customer> customers=customerRepository.findAll();
        List<customerDTO>customerDTOS= customers.stream().map(customer -> dtomapper.fromCustomer(customer)).collect(Collectors.toList());
        /* programamtion impereatif
     List<customerDTO> customerDTOS=new ArrayList<>();
       for(customer customer:customers){
          //

       }
       *

         */
       return customerDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException(""));
     if(bankAccount instanceof SavingAccount){
         SavingAccount savingAccount=(SavingAccount) bankAccount;
         return  dtomapper.fromSavingBankAccount(savingAccount);
     }
     else{
       CurrentAccount currentAccount =( CurrentAccount) bankAccount;
         return  dtomapper.fromCurrentBankAccount(currentAccount);
     }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException(""));
     if(bankAccount.getBalance()<amount)
       throw  new BalanceNotSufficientException("Balance not sufficient");

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperaTionType.Debit);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDAte(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);


    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException(""));
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperaTionType.Credit);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDAte(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource,amount,"Transfer to"+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from"+ accountIdSource);

    }
    @Override
    public List <BankAccountDTO>bankAccountList(){
    List<BankAccount>bankAccounts=bankAccountRepository.findAll();
    List<BankAccountDTO> bankAccountDTOS =bankAccounts.stream().map(bankAccount -> {
        if(bankAccount instanceof SavingAccount ){
           SavingAccount savingAccount=(SavingAccount) bankAccount;
            return  dtomapper.fromSavingBankAccount(savingAccount);

        }else{
           CurrentAccount currentAccount=(CurrentAccount) bankAccount;
            return  dtomapper.fromCurrentBankAccount(currentAccount);

        }
    }).collect(Collectors.toList());
return  bankAccountDTOS;
    }
    @Override
    public customerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
       customer customer= customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer not found") );
      return dtomapper.fromCustomer(customer);

    }
    @Override
    public customerDTO updateCustomer(customerDTO customerDTO) {

        log.info("Saving new Customer");
        customer customer=dtomapper.fromCustomerDTO(customerDTO);
        customer savedCustomer=customerRepository.save(customer);
        return dtomapper.fromCustomer(savedCustomer);
    }
    @Override
    public void deleteCustomer(long customerId){
        customerRepository.deleteById(customerId);
    }
   @Override
  public List<AccountOperationDTO> accountHistory(String accountId){
 List<AccountOperation>accountOperations=accountOperationRepository.findByBankAccountId(accountId) ;
 return  accountOperations.stream().map(op->dtomapper.fromaccountOperation(op)).collect(Collectors.toList());


  }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount == null)throw new BankAccountNotFoundException("Account not found");

       Page<AccountOperation> accountOperationPage=    accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page,size));
       AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
       List<AccountOperationDTO>accountOperationDTOS= accountOperationPage.getContent().stream().map(op->dtomapper.fromaccountOperation(op)).collect(Collectors.toList());
       accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
       accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperationPage.getTotalPages());
        return accountHistoryDTO;
    }
}
