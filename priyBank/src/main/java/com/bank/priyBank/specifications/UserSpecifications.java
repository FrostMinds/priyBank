package com.bank.priyBank.specifications;

import com.bank.priyBank.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserSpecifications {

    public static Specification<User> birthDateGreaterThan(LocalDate birthDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("birthDate"), birthDate);
    }

    public static Specification<User> phoneEquals(String phone) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("phone"), phone);
    }

    public static Specification<User> fullNameLike(String fullName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("fullName"), fullName + "%");
    }

    public static Specification<User> emailEquals(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email);
    }
}
