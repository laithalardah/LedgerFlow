package com.example.customerservice.service.impl;

import com.example.customerservice.entity.UserEntity;
import com.example.customerservice.mapper.UserMapper;
import com.example.customerservice.model.UserCreationModel;
import com.example.customerservice.model.UserModel;
import com.example.customerservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @InjectMocks private UserServiceImpl service;

    @Test void createsAndMapsUser() {
        UserCreationModel request = new UserCreationModel("Jane", "Doe", "jane", "jane@example.com", 24);
        UserEntity entity = new UserEntity(null, "Jane", "Doe", "jane", "jane@example.com", 24);
        UserEntity saved = new UserEntity(1L, "Jane", "Doe", "jane", "jane@example.com", 24);
        UserModel expected = new UserModel(1L, "jane", "Jane", "Doe", "jane@example.com", 24);
        when(userMapper.toUserEntity(request)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(saved);
        when(userMapper.toUserModel(saved)).thenReturn(expected);

        assertThat(service.createUser(request)).isEqualTo(expected);
        verify(userRepository).save(entity);
    }

    @Test void getsUserOrThrowsWhenMissing() {
        when(userRepository.findById(7L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getUserById(7L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test void mapsUserPage() {
        UserEntity entity = new UserEntity(1L, "Jane", "Doe", "jane", "jane@example.com", 24);
        UserModel model = new UserModel(1L, "jane", "Jane", "Doe", "jane@example.com", 24);
        var pageRequest = PageRequest.of(0, 10);
        when(userRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(List.of(entity), pageRequest, 1));
        when(userMapper.toUserModel(entity)).thenReturn(model);
        assertThat(service.getAllUsers(pageRequest).getContent()).containsExactly(model);
    }
}
