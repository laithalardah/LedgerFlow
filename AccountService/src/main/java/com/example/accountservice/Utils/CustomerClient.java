package com.example.accountservice.Utils;

import com.example.accountservice.exception.UserNotFoundException;
import com.example.accountservice.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

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
        catch (Exception e) {
            throw new UserNotFoundException("error trying to get user info");
        }
    }
}
