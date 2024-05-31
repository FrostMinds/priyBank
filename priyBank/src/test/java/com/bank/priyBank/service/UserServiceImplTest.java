package com.bank.priyBank.service;

import com.bank.priyBank.entity.BankAccount;
import com.bank.priyBank.entity.User;
import com.bank.priyBank.repository.BankAccountRepository;
import com.bank.priyBank.repository.UserRepository;
import com.bank.priyBank.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testTransferMoney() {

        User fromUser = new User();
        fromUser.setId(1L);
        BankAccount fromAccount = new BankAccount();
        fromAccount.setBalance(100.0);
        fromUser.setBankAccount(fromAccount);

        User toUser = new User();
        toUser.setId(2L);
        BankAccount toAccount = new BankAccount();
        toAccount.setBalance(50.0);
        toUser.setBankAccount(toAccount);

        when(userRepository.findById(1L)).thenReturn(Optional.of(fromUser));
        when(userRepository.findById(2L)).thenReturn(Optional.of(toUser));

        userService.transferMoney(1L, 2L, 50.0);

        verify(bankAccountRepository).save(fromAccount);
        verify(bankAccountRepository).save(toAccount);
    }
}
