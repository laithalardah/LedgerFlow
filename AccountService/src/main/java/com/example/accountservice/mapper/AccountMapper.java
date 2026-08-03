package com.example.accountservice.mapper;

import com.example.accountservice.entity.AccountEntity;
import com.example.accountservice.model.AccountCreationModel;
import com.example.accountservice.model.AccountModel;
import com.example.accountservice.resource.AccountCreationResource;
import com.example.accountservice.resource.AccountResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {


    AccountCreationModel toAccountCreationModel(AccountCreationResource accountCreationResource);

    // have to make sure this is a real userId first
    @Mapping(target = "userId" , ignore = true)
    @Mapping(target = "currency" , ignore = true)
    @Mapping(target =  "accountNumber" , ignore = true)
    @Mapping(target = "balance" , ignore = true)
    AccountEntity toAccountEntity(AccountCreationModel accountCreationModel);

    AccountModel toAccountModel(AccountEntity account);

    AccountResource toAccountResource(AccountModel accountModel);
}
