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

        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User existingUser = existingUserOpt.get();

        existingUser.setFirstName(createUserDto.getFirstName());
        existingUser.setLastName(createUserDto.getLastName());
        existingUser.setMail(createUserDto.getMail());
        existingUser.setNickname(createUserDto.getNickname());
        existingUser.setPhoto(createUserDto.getPhoto());

        User updatedUser = userRepository.save(existingUser);

        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String nickname, HttpSession session) {
        User user = userRepository.findByNickname(nickname);

        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }

        session.setAttribute("user", user);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).body(null);
        }

        return ResponseEntity.ok(user); // return the currently logged-in user
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // remove user from the session
        session.invalidate();
        return ResponseEntity.ok("User logged out successfully");
    }
}
