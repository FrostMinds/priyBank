package com.bank.priyBank.entity;

import jakarta.persistence.*;


@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;

    @Column(name = "initial_balance")
    private double initialBalance;

    // Конструкторы, геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    // Метод для увеличения баланса
    public void increaseBalanceByPercentage() {
        double maxIncrease = initialBalance * 2.07;
        double increaseAmount = balance * 0.05;

        // Проверка, что увеличение не превышает максимально допустимое
        if (balance + increaseAmount <= maxIncrease) {
            balance += increaseAmount;
        }
    }
}