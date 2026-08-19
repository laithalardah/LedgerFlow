package com.example.accountservice.controller;

import com.example.accountservice.mapper.AccountMapper;
import com.example.accountservice.mapper.UserMapper;
import com.example.accountservice.model.AccountCreationModel;
import com.example.accountservice.model.AccountModel;
import com.example.accountservice.model.UserModel;
import com.example.accountservice.resource.request.AccountCreationResource;
import com.example.accountservice.resource.response.AccountResource;
import com.example.accountservice.resource.request.AmountRequest;
import com.example.accountservice.resource.response.AccountValidationResponse;
import com.example.accountservice.resource.response.UserResource;
import com.example.accountservice.service.AccountService;
import com.example.accountservice.service.impl.AccountServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final UserMapper userMapper;

    AccountController (AccountService accountService , AccountMapper accountMapper, UserMapper userMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.userMapper = userMapper;
    }

    @Operation(description = "creates an account for the user with id given in the dto , inserts it into the accounts db")
    @PostMapping
    public ResponseEntity<AccountResource> createAccount(@RequestBody @Valid AccountCreationResource accountCreationResource) {

        log.info("Create Account Endpoint Invoked");
        AccountCreationModel accountCreationModel = accountMapper.toAccountCreationModel(accountCreationResource);

        AccountModel createdAccountModel = accountService.createAccount(accountCreationModel);

        return new ResponseEntity<>(accountMapper.toAccountResource(createdAccountModel) , HttpStatus.CREATED);
    }

    @Operation(description = "return the balance of an account using the accountNumber")
    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable Long accountNumber) {
        log.info("Get Account balance endpoint invoked");
        return ResponseEntity.ok(accountService.getAccountBalance(accountNumber));
    }


    @Operation(description = "WithDraw from Account")
    @PostMapping("/withdraw/{accountNumber}")
    public ResponseEntity<AccountResource> WithDraw(@PathVariable Long accountNumber ,
                                                    @RequestBody @Valid AmountRequest request) {
        log.info("withDraw Endpoint invoked");

        return ResponseEntity.ok(accountMapper.toAccountResource(accountService.withDraw(accountNumber , request)));
    }

    @Operation(description = "Deposit into Account")
    @PostMapping("/deposit/{accountNumber}")
    public ResponseEntity<AccountResource> Deposit(@PathVariable Long accountNumber ,

                                                   @RequestBody @Valid AmountRequest request) {
        log.info("deposit endpoints invoked");

        return ResponseEntity.ok(accountMapper.toAccountResource(accountService.deposit(accountNumber , request)));
    }

    @Operation(description = "Get All Accounts of a Specific User")
    @GetMapping("/{userId}")
    public ResponseEntity<List<AccountResource>> getUserAccounts(@PathVariable Long userId) {

        log.info("getUserAccounts invoked");

        List<AccountResource> accountResources = accountService.getUserAccounts(userId).stream()
                .map(accountMapper::toAccountResource)
                .toList();

        return ResponseEntity.ok(accountResources);
    }

    @Operation(description = "Get user Information of a specific account using account Number")
    @GetMapping("/{accountNumber}/userInformation")
    public ResponseEntity<UserResource> getAccountUserInfo(@PathVariable Long accountNumber) {

        log.info("getAccountUserInfo is invoked");

        UserModel userModel = accountService.getAccountUserInfo(accountNumber);

        return ResponseEntity.ok(userMapper.toUserResource(userModel));
    }

    @Operation(description = "Checks if the Account Number corresponds to a valid Account, Returns Currency and Account Number")
    @GetMapping("/{accountNumber}/validate")
    public ResponseEntity<AccountValidationResponse> validateAccount(@PathVariable Long accountNumber) {

        log.info("Validate Account Endpoint in invoked");

        AccountValidationResponse accountValidationResponse = accountService.validateAccount(accountNumber);

        log.info("Sending Validation Response");
        return ResponseEntity.ok(accountValidationResponse);
    }

}
