package com.bank.priyBank.service;

import com.bank.priyBank.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User createUser(String username, String password, double initialBalance, String phone, String email);
    User updateUserPhone(Long userId, String newPhone);
    User updateUserEmail(Long userId, String newEmail);
    void deleteUserPhone(Long userId);
    void deleteUserEmail(Long userId);
    List<User> searchUsers(LocalDate birthDate, String phone, String fullName, String email);
    void transferMoney(Long fromUserId, Long toUserId, double amount);
    void increaseBalanceByPercentage();
}
