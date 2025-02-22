package org.example.dresstoimpressservice.repository;

import org.example.dresstoimpressservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByNickname(String nickname);
}
