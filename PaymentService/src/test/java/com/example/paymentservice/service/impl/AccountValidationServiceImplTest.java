package com.example.paymentservice.service.impl;

import com.example.paymentservice.client.AccountClient;
import com.example.paymentservice.exception.BalanceNotFoundException;
import com.example.paymentservice.exception.InsufficientBalanceException;
import com.example.paymentservice.model.AccountModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountValidationServiceImplTest {
    @Mock private AccountClient accountClient;
    @InjectMocks private AccountValidationServiceImpl service;
    @Test void delegatesAccountValidation() {
        AccountModel account = new AccountModel(1L, Currency.getInstance("USD"));
        when(accountClient.validateAccount(1L)).thenReturn(account);
        assertThat(service.validateAccount(1L)).isEqualTo(account);
    }
    @Test void rejectsNullOrInsufficientBalance() {
        when(accountClient.getAccountBalance(1L)).thenReturn(null);
        assertThatThrownBy(() -> service.validateBalance(1L, BigDecimal.ONE)).isInstanceOf(BalanceNotFoundException.class);
        when(accountClient.getAccountBalance(1L)).thenReturn(BigDecimal.ONE);
        assertThatThrownBy(() -> service.validateBalance(1L, BigDecimal.TEN)).isInstanceOf(InsufficientBalanceException.class);
    }
    @Test void acceptsSufficientBalance() {
        when(accountClient.getAccountBalance(1L)).thenReturn(BigDecimal.TEN);
        assertThatCode(() -> service.validateBalance(1L, BigDecimal.TEN)).doesNotThrowAnyException();
    }
}
