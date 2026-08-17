package com.example.transactionhistoryservice.mapper;

import com.example.transactionhistoryservice.enums.ReferenceType;
import com.example.transactionhistoryservice.exception.InvalidReferenceType;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ReferenceTypeMapper {

    public ReferenceType toReferenceType(String type) {
        try {
            return ReferenceType.valueOf(type.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new InvalidReferenceType("Invalid Reference Type: " + type);
        }
    }
}
