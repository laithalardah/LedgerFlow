package com.example.accountservice.client;

import com.example.accountservice.exception.CustomerServiceUnavailableException;
import com.example.accountservice.exception.UserNotFoundException;
import com.example.accountservice.model.UserModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerClient {

    private final RestTemplate restTemplate;

    CustomerClient (RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserModel getUserInfo(Long userId) {
        String customer_service_url = "http://localhost:8080/users/"+userId;
        try {
            return restTemplate.getForObject(customer_service_url , UserModel.class);
        }
        catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("error trying to get user info");
        }
        catch (RestClientException e) {
            throw new CustomerServiceUnavailableException("customer service unavailable");
        }
    }
}
