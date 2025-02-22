package org.example.dresstoimpressservice.controller;

import org.example.dresstoimpressservice.dto.CreateShowDto;
import org.example.dresstoimpressservice.dto.CreatingStylingDto;
import org.example.dresstoimpressservice.model.Show;
import org.example.dresstoimpressservice.model.Styling;
import org.example.dresstoimpressservice.model.User;
import org.example.dresstoimpressservice.repository.ShowRepository;
import org.example.dresstoimpressservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowRepository showRepository;
    private final UserRepository userRepository;

    public ShowController(ShowRepository showRepository, UserRepository userRepository) {
        this.showRepository = showRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    List<Show> findAll() {
        return showRepository.findAll();
    }

    @PostMapping
    Show createShow(@RequestBody CreateShowDto showDto) {
        Show show = new Show();
        show.setTopic(showDto.getTopic());
        show.setCreatorId(showDto.getCreatorId());
        show.setMaxParticipantsNumber(showDto.getMaxParticipantsNumber());
        show.setJoiningDate(showDto.getJoiningDate());
        show.setVotingTime(showDto.getVotingTime());
        show.setBanner(showDto.getBanner());
        Show savedShow = showRepository.save(show);
        return savedShow;
    }

    //adds styling to the show
    @PostMapping("/stylings")
    Styling createStyling(@RequestBody CreatingStylingDto stylingDto) {
        Show show = showRepository.findById(stylingDto.getShowId()).orElseThrow();
        User user = userRepository.findById(stylingDto.getUserId()).orElseThrow();
        Styling styling = new Styling();
        styling.setName(stylingDto.getName());
        styling.setDescription(stylingDto.getDescription());
        styling.setPhoto(stylingDto.getPhoto());
        show.addStyling(styling);
        user.addStyling(styling);
        showRepository.save(show);
        return styling;
    }

    @GetMapping("/by-category")
    public List<Show> getShowsByCategory(@RequestParam String name) {
        return showRepository.findByCategoryName(name);
    }

    @GetMapping("/by-topic")
    public List<Show> getShowsByTopic(@RequestParam String topic) {
        return showRepository.findByTopicIgnoreCase(topic);
    }

    @GetMapping("/by-creator")
    public List<Show> getShowsByCreator(@RequestParam Long creatorId) {
        return showRepository.findByCreatorId(creatorId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShow(@PathVariable Long id) {
        if (showRepository.existsById(id)) {
            showRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Show has been successfully removed.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Show with the given ID does not exist.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Show> updateShow(@PathVariable Long id, @RequestBody CreateShowDto showDto) {
        Show existingShow = showRepository.findById(id).orElse(null);

        if (existingShow == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        existingShow.setTopic(showDto.getTopic());
        existingShow.setCreatorId(showDto.getCreatorId());
        existingShow.setMaxParticipantsNumber(showDto.getMaxParticipantsNumber());
        existingShow.setJoiningDate(showDto.getJoiningDate());
        existingShow.setVotingTime(showDto.getVotingTime());
        existingShow.setBanner(showDto.getBanner());

        Show updatedShow = showRepository.save(existingShow);

        return ResponseEntity.ok(updatedShow);
    }
}
