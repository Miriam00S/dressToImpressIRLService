package org.example.dresstoimpressservice.repository;

import org.example.dresstoimpressservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
