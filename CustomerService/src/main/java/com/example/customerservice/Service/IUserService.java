package com.example.customerservice.Service;

import com.example.customerservice.Model.Dto.UserCreationDto;
import com.example.customerservice.Model.Dto.UserDto;


import java.util.List;

public interface IUserService {

    UserDto createUser(UserCreationDto userCreationDto);

    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();

}
