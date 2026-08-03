package com.example.accountservice.mapper;

import com.example.accountservice.model.UserModel;
import com.example.accountservice.resource.UserResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResource toUserResource(UserModel userModel);
    UserModel toUserModel(UserResource userResource);
}
