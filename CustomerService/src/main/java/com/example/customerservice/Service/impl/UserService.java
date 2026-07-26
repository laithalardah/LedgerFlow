package com.example.customerservice.Service.impl;
import com.example.customerservice.Model.Dto.UserCreationDto;
import com.example.customerservice.Model.Dto.UserDto;
import com.example.customerservice.Model.Entity.User;
import com.example.customerservice.Model.Mapper.UserMapper;
import com.example.customerservice.Repository.UserRepository;
import com.example.customerservice.Service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    UserService(UserRepository userRepository , UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserCreationDto userCreationDto) {


        if(userRepository.existsByEmail(userCreationDto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT , "email already in use");
        }

        if(userRepository.existsByUserName(userCreationDto.userName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT , "userName already in use");
        }

        User user = userMapper.mapToUser(userCreationDto);


        System.out.println(user.getId());
        User savedUser = userRepository.save(user);
        System.out.println(savedUser.getId());



        return userMapper.maptoUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found");
                });

        return userMapper.maptoUserDto(user);
    }


    @Override
    public List<UserDto> getAllUsers() {

        //my first usage of streams irl haha
        List<UserDto> usersDto = userRepository.findAll().stream()
                .map((user)-> userMapper.maptoUserDto(user))
                .toList();

        return usersDto;
    }
}
