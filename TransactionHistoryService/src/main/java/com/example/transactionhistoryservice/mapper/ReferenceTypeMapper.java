package com.example.transactionhistoryservice.mapper;

import com.example.transactionhistoryservice.enums.ReferenceType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReferenceTypeMapper {

    private final Map<String , ReferenceType> types = Map.of("Transfer" , ReferenceType.Transfer,
            "Payment" , ReferenceType.Payment);

    public ReferenceType toReferenceType(String type) {
        return types.get(type);
    }
}
