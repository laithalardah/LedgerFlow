package com.example.accountservice.mapper;

import com.example.accountservice.exception.InvalidCurrencyException;


import java.util.Currency;

public class CurrencyMapper {


    public static Currency currencySymbolMapping(String currencySymbol){

        Currency currency;

        try {
            currency = Currency.getInstance(currencySymbol);
        } catch (Exception e) {
            throw new InvalidCurrencyException("no currency with symbol " + currencySymbol);
        }

        return currency;
    }
}
