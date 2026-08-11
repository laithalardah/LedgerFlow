package com.example.transactionhistoryservice.client;

import com.example.transactionhistoryservice.exception.InvalidUserException;
import com.example.transactionhistoryservice.model.AccountModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AccountClient {

    private final RestTemplate restTemplate;
    @Value("${account-service.url}")
    String ACCOUNT_SERVICE_URL_PREFIX;

    private AccountClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<AccountModel> getUserAccounts(Long id) {
        String endpoint = ACCOUNT_SERVICE_URL_PREFIX + "/" + id;

        try {
            return restTemplate.exchange(
                    endpoint,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<AccountModel>>() {}
            ).getBody();
        }
        catch (HttpClientErrorException.NotFound e) {
            throw new InvalidUserException("User Not Found");
        }
        catch (RestClientException e) {
            throw new RuntimeException("Account Service Currently Not Available");
        }

    }




}
