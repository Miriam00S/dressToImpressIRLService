package org.example.dresstoimpressservice.controller;

import org.example.dresstoimpressservice.dto.CreateShowDto;
import org.example.dresstoimpressservice.dto.CreatingStylingDto;
import org.example.dresstoimpressservice.model.Show;
import org.example.dresstoimpressservice.model.Styling;
import org.example.dresstoimpressservice.repository.ShowRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowRepository showRepository;


    public ShowController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @PostMapping
    Show createShow(@RequestBody CreateShowDto showDto) {
        Show show = new Show();
        show.setTopic(showDto.getTopic());
        show.setCreatorId(showDto.getCreatorId());
        Show savedShow = showRepository.save(show);
        return savedShow;
    }

    @PostMapping("/stylings")
    Styling createStyling(@RequestBody CreatingStylingDto stylingDto) {
        Show show = showRepository.findById(stylingDto.getShowId()).orElseThrow();
        Styling styling = new Styling();
        styling.setUserId(stylingDto.getUserId());
        styling.setName(stylingDto.getName());
        show.addStyling(styling);
        showRepository.save(show);
        return styling;
    }


}
