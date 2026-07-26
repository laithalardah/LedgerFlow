package com.example.customerservice.Controller;


import com.example.customerservice.Model.Dto.UserCreationDto;
import com.example.customerservice.Model.Dto.UserDto;
import com.example.customerservice.Service.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUser() {

        List<UserDto> allUsers = new ArrayList<>(userService.getAllUsers());
        return ResponseEntity.ok(allUsers);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserCreationDto userCreationDto) {
        return new ResponseEntity<UserDto>(userService.createUser(userCreationDto) , HttpStatus.CREATED);
    }

}
