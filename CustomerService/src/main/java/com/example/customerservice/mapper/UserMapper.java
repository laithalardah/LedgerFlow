package com.example.customerservice.mapper;

import com.example.customerservice.entity.UserEntity;
import com.example.customerservice.model.UserCreationModel;
import com.example.customerservice.model.UserModel;
import com.example.customerservice.resource.request.UserCreationResource;
import com.example.customerservice.resource.response.UserResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id" , ignore = true)
    UserEntity toUserEntity(UserCreationModel userCreationModel);

    UserCreationModel toUserCreationModel(UserCreationResource userCreationResource);

    UserResource toUserResource(UserModel userModel);
    
    UserModel toUserModel(UserEntity user);





}
