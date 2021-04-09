package org.newton.webshop.services.combined;

import org.newton.webshop.models.dto.creation.AccountCreationDto;
import org.newton.webshop.models.dto.response.AccountDetailsDto;
import org.newton.webshop.models.dto.response.AccountDto;
import org.newton.webshop.models.dto.update.CustomerUpdateDto;
import org.newton.webshop.models.entities.Account;
import org.newton.webshop.models.entities.Address;
import org.newton.webshop.models.entities.Customer;
import org.newton.webshop.repositories.AccountRepository;
import org.newton.webshop.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.HashSet;


@Service
public class AccountService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Find account details by account id
     *
     * @param id account id
     * @return dto containing account details
     */
    public AccountDetailsDto findById(String id) {
        return new AccountDetailsDto(accountRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    /**
     * Creates an Account entity (login details) and an associated Customer entity (personal details like name and adress)
     *
     * @param accountCreationDto dto containing all details needed
     * @return dto, including ids of created entites
     */
    public AccountDto addAccount(@RequestBody AccountCreationDto accountCreationDto) {
        var customer = toEntity(accountCreationDto);
        customerRepository.save(customer);

        var account = toEntity(accountCreationDto, customer);
        accountRepository.save(account);

        return toDto(account);
    }

    /**
     * Updates an Account entity and the associated Customer entity
     *
     * @param accountId         id of account that should be updated
     * @param customerUpdateDto dto containing the fields that can be updated
     * @return dto
     */
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

    /**
     * Delete account by account id
     *
     * @param id account id
     */
    public void deleteAccountById(String id) {
        Account deleteAccount = accountRepository.findById(id).orElseThrow(RuntimeException::new);
        accountRepository.delete(deleteAccount);
    }

    /**
     * Converts dto to a Customer entity.
     * Leaves orders-field as an empty HashSet.
     * Leaves account-field as null.
     *
     * @param dto contains details required to create the two entities
     * @return customer entity. Use getAccount-method to access account entity
     */
    private static Customer toEntity(AccountCreationDto dto) {
        //Build customer entity
        var customer = Customer.builder()
                .orders(new HashSet<>())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .address(Address.builder()
                        .streetName(dto.getStreetName())
                        .streetNumber(dto.getStreetNumber())
                        .zipCode(dto.getZipCode())
                        .city(dto.getCity())
                        .build())
                .build();

        //Return customer entity, containing account entity
        return customer;
    }

    /**
     * Convert dto to Account entity and sets the bidirectional association with Customer entity given as parameter.
     * The Customer entity should already be persisted, otherwise the returned Account entity cannot be persisted.
     *
     * @param dto      contains information required to create an account
     * @param customer already persisted Customer entity
     * @return Account entity
     */
    private static Account toEntity(AccountCreationDto dto, Customer customer) {
        //Creation date is always the current date
        var creationDate = LocalDate.now();

        //Build account entity
        var account = Account.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .createDate(creationDate)
                .birthDate(dto.getBirthDate())
                .build();

        //Set association both ways
        customer.setAccountAssociation(account);

        //Return customer entity, containing account entity
        return account;
    }

    /**
     * Converts Account entity to a AccountDto, which bundles the details in an Account entity and a Customer entity
     *
     * @param account Account entity to convert
     * @return ResponseDto that bundles
     * @throws NullPointerException if customer-field is null, or the address-field in the customer is null.
     */
    private static AccountDto toDto(Account account) {
        var customer = account.getCustomer();
        var address = customer.getAddress();

        return AccountDto.builder()
                .accountId(account.getId())
                .customerId(customer.getId())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .streetName(address.getStreetName())
                .streetNumber(address.getStreetNumber())
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .username(account.getUsername())
                .birthDate(account.getBirthDate())
                .build();
    }
}
