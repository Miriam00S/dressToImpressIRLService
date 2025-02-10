package org.example.dresstoimpressservice.controller;


import org.example.dresstoimpressservice.dto.CreateVoteDto;
import org.example.dresstoimpressservice.model.Styling;
import org.example.dresstoimpressservice.model.Vote;
import org.example.dresstoimpressservice.repository.StylingRepository;
import org.example.dresstoimpressservice.repository.VoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteRepository voteRepository;
    private final StylingRepository stylingRepository;


    public VoteController(VoteRepository voteRepository, StylingRepository stylingRepository) {
        this.voteRepository = voteRepository;
        this.stylingRepository = stylingRepository;
    }

    @PostMapping
    public ResponseEntity<Vote> addVote(@RequestBody CreateVoteDto voteDto) {
        Styling styling = stylingRepository.findById(voteDto.getStylingId())
                .orElseThrow(() -> new RuntimeException("Styling not found with id: " + voteDto.getStylingId()));

        Vote vote = new Vote();
        vote.setPoints(voteDto.getPoints());
        vote.setStyling(styling);

        Vote savedVote = voteRepository.save(vote);
        return ResponseEntity.ok(savedVote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVote(@PathVariable Long id) {
        // Znalezienie głosu po ID
        Optional<Vote> optionalVote = voteRepository.findById(id);

        if (optionalVote.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vote o podanym ID nie istnieje.");
        }

        // Usunięcie głosu
        voteRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
