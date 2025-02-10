package org.example.dresstoimpressservice.controller;

import jakarta.servlet.http.HttpSession;
import org.example.dresstoimpressservice.dto.CreateUserDto;
import org.example.dresstoimpressservice.model.User;
import org.example.dresstoimpressservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/hello")
    String hello() {
        return "hello";
    }

    @GetMapping
    List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<User> findById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody CreateUserDto createUserDto) {
        // Sprawdzamy, czy użytkownik o podanym ID istnieje
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Pobieramy istniejącego użytkownika
        User existingUser = existingUserOpt.get();

        // Aktualizujemy dane użytkownika
        existingUser.setFirstName(createUserDto.getFirstName());
        existingUser.setLastName(createUserDto.getLastName());
        existingUser.setMail(createUserDto.getMail());
        existingUser.setNickname(createUserDto.getNickname());
        existingUser.setPhoto(createUserDto.getPhoto());

        // Zapisujemy zaktualizowanego użytkownika w bazie
        User updatedUser = userRepository.save(existingUser);

        // Zwracamy zaktualizowanego użytkownika
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String nickname, HttpSession session) {
        User user = userRepository.findByNickname(nickname);

        if (user == null) {
            return ResponseEntity.status(404).body(null);  // Zwracamy 404 jeśli użytkownik nie istnieje
        }

        session.setAttribute("user", user);

        return ResponseEntity.ok(user);  // Zwracamy użytkownika
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).body(null);  // Użytkownik nie jest zalogowany
        }

        return ResponseEntity.ok(user);  // Zwracamy aktualnie zalogowanego użytkownika
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // Usuwamy użytkownika z sesji
        session.invalidate();
        return ResponseEntity.ok("User logged out successfully");
    }
}
