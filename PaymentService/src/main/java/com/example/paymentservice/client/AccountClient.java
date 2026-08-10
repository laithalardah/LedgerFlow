package com.example.paymentservice.client;

import com.example.paymentservice.exception.AccountServiceNotAvailableException;
import com.example.paymentservice.exception.InvalidAccountException;
import com.example.paymentservice.model.AccountModel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Slf4j
@Service
public class AccountClient {

    private final RestTemplate restTemplate;

    @Value("${account-service.url}")
    private String ACCOUNT_URL_PREFIX;
    public AccountClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Not sure about the design
    public AccountModel validateAccount(Long id) {
        String endpoint = ACCOUNT_URL_PREFIX + "/accounts/" + id + "/validate";

        log.info("Calling Account Service to Get Validation...");

        try{
            return restTemplate.getForObject(endpoint , AccountModel.class);
        }
        catch (HttpClientErrorException.NotFound e) {
            throw new InvalidAccountException("Account With Number " + id + " Does Not Exist");
        }
        catch (RestClientException e) {
            throw new AccountServiceNotAvailableException("Account Service Is Currently Not Available");
        }
    }

    public BigDecimal getAccountBalance(Long id) {
        String endpoint = ACCOUNT_URL_PREFIX + "/accounts/balance/" + id;

        log.info("Calling Account Service to get Balance...");

        try{
            return restTemplate.getForObject(endpoint ,BigDecimal.class);
        }
        catch (HttpClientErrorException.NotFound e) {
            throw new InvalidAccountException("Account With Number " + id + " Does Not Exist");
        }
        catch (RestClientException e) {
            throw new AccountServiceNotAvailableException("Account Service Is Currently Not Available");
        }

    }
}
