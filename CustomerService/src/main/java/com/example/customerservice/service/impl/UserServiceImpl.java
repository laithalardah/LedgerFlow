package com.example.customerservice.service.impl;

import com.example.customerservice.entity.UserEntity;
import com.example.customerservice.mapper.UserMapper;
import com.example.customerservice.model.UserCreationModel;
import com.example.customerservice.model.UserModel;
import com.example.customerservice.repository.UserRepository;
import com.example.customerservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserModel createUser(UserCreationModel userCreationModel) {

        log.info("Creating user..");
        UserEntity user = userMapper.toUserEntity(userCreationModel);

        UserEntity savedUser = userRepository.save(user);

        log.info("User Created!");
        return userMapper.toUserModel(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserModel getUserById(Long id) {

        log.info("getting user by Id");
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found an id with value " + id));

        return userMapper.toUserModel(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserModel> getAllUsers(Pageable pageable) {

        log.info("getting all users");

        return userRepository.findAll(pageable)
                .map(userMapper::toUserModel);
    }
}
