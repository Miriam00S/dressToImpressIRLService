package org.example.dresstoimpressservice.repository;

import org.example.dresstoimpressservice.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
