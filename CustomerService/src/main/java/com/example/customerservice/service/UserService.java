package com.example.customerservice.service;

import com.example.customerservice.model.UserCreationModel;
import com.example.customerservice.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserModel createUser(UserCreationModel userCreationModel);

    UserModel getUserById(Long id);

    Page<UserModel> getAllUsers(Pageable pageable);

}
