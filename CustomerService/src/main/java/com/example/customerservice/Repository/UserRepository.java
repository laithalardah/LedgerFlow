package com.example.customerservice.Repository;

import com.example.customerservice.Model.Entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User , Long> {

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

}
