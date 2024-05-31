package com.bank.priyBank.controller;

import com.bank.priyBank.entity.User;
import com.bank.priyBank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user.getFullName(), user.getPassword(),
                user.getBankAccount().getBalance(), user.getPhone(), user.getEmail());
    }

    @PutMapping("/{userId}/phone")
    public User updateUserPhone(@PathVariable Long userId, @RequestParam String newPhone) {
        return userService.updateUserPhone(userId, newPhone);
    }

    @PutMapping("/{userId}/email")
    public User updateUserEmail(@PathVariable Long userId, @RequestParam String newEmail) {
        return userService.updateUserEmail(userId, newEmail);
    }

    @DeleteMapping("/{userId}/phone")
    public void deleteUserPhone(@PathVariable Long userId) {
        userService.deleteUserPhone(userId);
    }

    @DeleteMapping("/{userId}/email")
    public void deleteUserEmail(@PathVariable Long userId) {
        userService.deleteUserEmail(userId);
    }

    @GetMapping("/search")
    public List<User> searchUsers(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email) {
        return userService.searchUsers(birthDate, phone, fullName, email);
    }

    @PostMapping("/{fromUserId}/transfer/{toUserId}")
    public void transferMoney(@PathVariable Long fromUserId, @PathVariable Long toUserId, @RequestParam double amount) {
        userService.transferMoney(fromUserId, toUserId, amount);
    }
}
