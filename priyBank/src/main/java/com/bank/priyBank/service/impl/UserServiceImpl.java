package com.bank.priyBank.service.impl;

import com.bank.priyBank.entity.BankAccount;
import com.bank.priyBank.entity.User;
import com.bank.priyBank.repository.BankAccountRepository;
import com.bank.priyBank.repository.UserRepository;
import com.bank.priyBank.service.UserService;
import com.bank.priyBank.specifications.UserSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BankAccountRepository bankAccountRepository) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    @Transactional
    public User createUser(String fullName, String password, double initialBalance, String phone, String email) {
        if (userRepository.existsByPhone(phone) || userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Phone or email already exists");
        }

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(initialBalance);
        bankAccountRepository.save(bankAccount);

        User user = new User();
        user.setFullName(fullName);
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);
        user.setBankAccount(bankAccount);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUserPhone(Long userId, String newPhone) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getPhone().equals(newPhone) && userRepository.existsByPhone(newPhone)) {
            throw new IllegalArgumentException("Phone already exists");
        }

        user.setPhone(newPhone);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUserEmail(Long userId, String newEmail) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Проверка уникальности нового email
        if (!user.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserPhone(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userRepository.countByPhone(user.getPhone()) == 1) {
            throw new IllegalArgumentException("Cannot delete the last phone number");
        }

        user.setPhone(null);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserEmail(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userRepository.countByEmail(user.getEmail()) == 1) {
            throw new IllegalArgumentException("Cannot delete the last email");
        }

        user.setEmail(null);
        userRepository.save(user);
    }

    @Override
    public List<User> searchUsers(LocalDate birthDate, String phone, String fullName, String email) {
        Specification<User> specification = Specification.where(null);

        if (birthDate != null) {
            specification = specification.and(UserSpecifications.birthDateGreaterThan(birthDate));
        }

        if (phone != null) {
            specification = specification.and(UserSpecifications.phoneEquals(phone));
        }

        if (fullName != null) {
            specification = specification.and(UserSpecifications.fullNameLike(fullName));
        }

        if (email != null) {
            specification = specification.and(UserSpecifications.emailEquals(email));
        }

        return userRepository.findAll(specification);
    }

    @Override
    @Transactional
    public void transferMoney(Long fromUserId, Long toUserId, double amount) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("Source user not found"));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new IllegalArgumentException("Destination user not found"));

        BankAccount fromAccount = fromUser.getBankAccount();
        BankAccount toAccount = toUser.getBankAccount();

        if (fromAccount.getBalance() < amount) {
            throw new IllegalStateException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);
    }

    @Override
    @Transactional
    public void increaseBalanceByPercentage() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        for (BankAccount account : accounts) {
            account.increaseBalanceByPercentage();
            bankAccountRepository.save(account);
        }
    }
}
