package com.example.accountservice.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Currency;

public class CurrencyMapper {


    public static Currency currencySymbolMapping(String currencySymbol){

        Currency currency;

        try {
            currency = Currency.getInstance(currencySymbol);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "not valid currency");
        }

        return currency;
    }
}
