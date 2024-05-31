package com.bank.priyBank.repository;

import com.bank.priyBank.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
    long countByPhone(String phone);
    long countByEmail(String email);
    List<User> findAll(Specification<User> specification);
}
