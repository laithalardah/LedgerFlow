package com.example.paymentservice.mapper;

import com.example.paymentservice.entity.TransferEntity;
import com.example.paymentservice.model.TransferCreationModel;
import com.example.paymentservice.model.TransferModel;
import com.example.paymentservice.resource.TransferCreationResource;
import com.example.paymentservice.resource.TransferResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    TransferCreationModel toTransferCreationModel(TransferCreationResource transferCreationResource);

    @Mapping(target = "status" , ignore = true)
    @Mapping(target = "id" , ignore = true)
    TransferEntity toTransferEntity(TransferCreationModel transferCreationModel);

    TransferModel toTransferModel(TransferEntity transferEntity);

    TransferResource toTransferResource(TransferModel transferModel);
}
