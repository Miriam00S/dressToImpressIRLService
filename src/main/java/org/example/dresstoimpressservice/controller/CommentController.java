package org.example.dresstoimpressservice.controller;

import org.example.dresstoimpressservice.dto.CreateCommentDto;
import org.example.dresstoimpressservice.model.Comment;
import org.example.dresstoimpressservice.model.Show;
import org.example.dresstoimpressservice.model.User;
import org.example.dresstoimpressservice.repository.CommentRepository;
import org.example.dresstoimpressservice.repository.ShowRepository;
import org.example.dresstoimpressservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;

    public CommentController(CommentRepository commentRepository, ShowRepository showRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CreateCommentDto createCommentDto) {
        // Znajdź show po ID
        Optional<Show> optionalShow = showRepository.findById(createCommentDto.getShowId());

        if (optionalShow.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Show o podanym ID nie istnieje.");
        }

        Show show = optionalShow.get();

        // Znajdź user po ID
        Optional<User> optionalUser = userRepository.findById(createCommentDto.getUserId());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Show o podanym ID nie istnieje.");
        }

        User user = optionalUser.get();

        // Tworzenie nowego komentarza
        Comment comment = new Comment();
        comment.setMessage(createCommentDto.getMessage());
        comment.setTime(createCommentDto.getTime() != null ? createCommentDto.getTime() : LocalDateTime.now());
        comment.setUser(user);
        comment.setShow(show);

        // Zapis do bazy danych
        commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }
}
