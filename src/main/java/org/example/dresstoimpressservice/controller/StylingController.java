package org.example.dresstoimpressservice.controller;

import org.example.dresstoimpressservice.dto.CreatingStylingDto;
import org.example.dresstoimpressservice.model.Show;
import org.example.dresstoimpressservice.model.Styling;
import org.example.dresstoimpressservice.model.User;
import org.example.dresstoimpressservice.repository.ShowRepository;
import org.example.dresstoimpressservice.repository.StylingRepository;
import org.example.dresstoimpressservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/stylings")
public class StylingController {

    private final StylingRepository stylingRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;

    public StylingController(StylingRepository stylingRepository, ShowRepository showRepository, UserRepository userRepository) {
        this.stylingRepository = stylingRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStyling(@PathVariable Long id, @RequestBody CreatingStylingDto stylingDto) {
        Optional<Styling> optionalStyling = stylingRepository.findById(id);
        if (optionalStyling.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Styling with the given ID does not exist.");
        }
        Styling styling = optionalStyling.get();

        styling.setName(stylingDto.getName());
        styling.setDescription(stylingDto.getDescription());
        styling.setPhoto(stylingDto.getPhoto());
        styling.setPrivate(stylingDto.getPrivate());

        if (stylingDto.getShowId() != null) {
            Optional<Show> optionalShow = showRepository.findById(stylingDto.getShowId());
            if (optionalShow.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Show with the given ID does not exist.");
            }
            styling.setShow(optionalShow.get());
        }

        if (stylingDto.getUserId() != null) {
            Optional<User> optionalUser = userRepository.findById(stylingDto.getUserId());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with the given ID does not exist.");
            }
            styling.setUser(optionalUser.get());
        }

        Styling updatedStyling = stylingRepository.save(styling);

        return ResponseEntity.ok(updatedStyling);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStyling(@PathVariable Long id) {

        Optional<Styling> optionalStyling = stylingRepository.findById(id);

        if (optionalStyling.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Styling with the given ID does not exist.");
        }

        stylingRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Status 204 No Content
    }

}
