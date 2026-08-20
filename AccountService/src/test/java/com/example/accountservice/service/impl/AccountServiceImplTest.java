package com.example.accountservice.service.impl;

import com.example.accountservice.client.CustomerClient;
import com.example.accountservice.entity.AccountEntity;
import com.example.accountservice.enums.AccountType;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.exception.InsufficientBalanceException;
import com.example.accountservice.mapper.AccountMapper;
import com.example.accountservice.messaging.TransferEventPublisher;
import com.example.accountservice.model.AccountModel;
import com.example.accountservice.model.UserModel;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.resource.request.AmountRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock private AccountRepository accountRepository;
    @Mock private AccountMapper accountMapper;
    @Mock private CustomerClient customerClient;
    @Mock private TransferEventPublisher transferEventPublisher;
    @InjectMocks private AccountServiceImpl service;
    @Test void getsBalanceOrThrowsWhenAccountIsMissing() {
        AccountEntity account = account(1L, "10.00");
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThat(service.getAccountBalance(1L)).isEqualByComparingTo("10.00");
        when(accountRepository.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getAccountBalance(2L)).isInstanceOf(AccountNotFoundException.class);
    }
    @Test void depositsAndWithdrawsFunds() {
        AccountEntity account = account(1L, "10.00");
        AccountModel model = new AccountModel(1L, AccountType.Savings, Currency.getInstance("USD"), 5L, new BigDecimal("15.00"));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountMapper.toAccountModel(account)).thenReturn(model);
        assertThat(service.deposit(1L, new AmountRequest(new BigDecimal("5.00")))).isEqualTo(model);
        assertThat(account.getBalance()).isEqualByComparingTo("15.00");
        assertThat(service.withDraw(1L, new AmountRequest(new BigDecimal("5.00")))).isEqualTo(model);
        assertThat(account.getBalance()).isEqualByComparingTo("10.00");
        verify(accountRepository, times(2)).save(account);
    }
    @Test void rejectsOverdraftAndMapsUserAccounts() {
        AccountEntity account = account(1L, "10.00");
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertThatThrownBy(() -> service.withDraw(1L, new AmountRequest(new BigDecimal("11.00"))))
                .isInstanceOf(InsufficientBalanceException.class);
        UserModel user = new UserModel(5L, "jane", "Jane", "Doe", "jane@example.com");
        AccountModel model = new AccountModel(1L, AccountType.Savings, Currency.getInstance("USD"), 5L, BigDecimal.TEN);
        when(customerClient.getUserInfo(5L)).thenReturn(user);
        when(accountRepository.findAllByUserId(5L)).thenReturn(List.of(account));
        when(accountMapper.toAccountModel(account)).thenReturn(model);
        assertThat(service.getUserAccounts(5L)).containsExactly(model);
    }
    private AccountEntity account(Long id, String balance) {
        AccountEntity account = new AccountEntity(); account.setAccountNumber(id); account.setBalance(new BigDecimal(balance));
        account.setAccountType(AccountType.Savings); account.setCurrency(Currency.getInstance("USD")); account.setUserId(5L); return account;
    }
}
