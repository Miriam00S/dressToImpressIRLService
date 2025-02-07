package org.example.dresstoimpressservice.controller;

import org.example.dresstoimpressservice.dto.CreateShowDto;
import org.example.dresstoimpressservice.dto.CreatingStylingDto;
import org.example.dresstoimpressservice.model.Show;
import org.example.dresstoimpressservice.model.Styling;
import org.example.dresstoimpressservice.model.User;
import org.example.dresstoimpressservice.repository.ShowRepository;
import org.example.dresstoimpressservice.repository.UserRepository;
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

    //dodaje stylizacje do show
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
}
