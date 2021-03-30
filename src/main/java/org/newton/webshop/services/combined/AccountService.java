package org.newton.webshop.services.combined;

import org.newton.webshop.models.dto.creation.AccountCreationDto;
import org.newton.webshop.models.dto.update.CustomerUpdateDto;
import org.newton.webshop.models.dto.response.AccountDetailsDto;
import org.newton.webshop.models.dto.response.AccountDto;
import org.newton.webshop.models.entities.Account;
import org.newton.webshop.models.entities.Customer;
import org.newton.webshop.repositories.AccountRepository;
import org.newton.webshop.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;


@Service
public class AccountService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    //Customer wants to see the account
    public AccountDetailsDto findById(String id) {
        return new AccountDetailsDto(accountRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    //Customer wants to create an account
    public AccountDto addAccount(@RequestBody AccountCreationDto accountCreationDto) {
        Customer createCustomer = new Customer(accountCreationDto);
        customerRepository.save(createCustomer);

        Account createAccount = new Account(accountCreationDto, createCustomer, LocalDate.now());
        return new AccountDto(accountRepository.save(createAccount));
    }

    //Customer wants to modify personal information
    public CustomerUpdateDto editCustomerByAccountId(String accountId, CustomerUpdateDto customerUpdateDto) {
        Customer updatedCustomer = customerRepository.findCustomerByAccount_Id(accountId)
                .map(customer -> {
                    customer.setFirstname(customerUpdateDto.getFirstname());
                    customer.setLastname(customerUpdateDto.getLastname());
                    customer.setPhone(customerUpdateDto.getPhone());
                    customer.getAddress().setStreetName(customerUpdateDto.getStreetName());
                    customer.getAddress().setStreetNumber(customerUpdateDto.getStreetNumber());
                    customer.getAddress().setZipCode(customerUpdateDto.getZipCode());
                    customer.getAddress().setCity(customerUpdateDto.getCity());
                    return customerRepository.save(customer);
                }).orElseThrow(RuntimeException::new);
        return new CustomerUpdateDto(updatedCustomer);
    }

    //Customer wants to delete account
    public void deleteAccountById(String id) {
        Account deleteAccount = accountRepository.findById(id).orElseThrow(RuntimeException::new);
        accountRepository.delete(deleteAccount);
    }
}
