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
import org.springframework.web.bind.annotation.*;

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
        comment.setEdited(createCommentDto.getEdited());
        comment.setUser(user);
        comment.setShow(show);

        // Zapis do bazy danych
        commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CreateCommentDto updateCommentDto) {
        // Znalezienie komentarza po ID
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Komentarz o podanym ID nie istnieje.");
        }

        Comment comment = optionalComment.get();

        // Aktualizacja treści komentarza
        if (updateCommentDto.getMessage() != null) {
            comment.setMessage(updateCommentDto.getMessage());
        }

        // Aktualizacja daty edycji (jeśli nie podano, ustawiamy na teraz)
        comment.setTime(updateCommentDto.getTime() != null ? updateCommentDto.getTime() : LocalDateTime.now());

        // Ustawienie flagi `isEdited` na `true`
        comment.setEdited(true);

        // Zapis zmian do bazy danych
        commentRepository.save(comment);

        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        // Znalezienie komentarza po ID
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Komentarz o podanym ID nie istnieje.");
        }

        // Usunięcie komentarza
        commentRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
