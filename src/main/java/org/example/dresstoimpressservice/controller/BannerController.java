package org.example.dresstoimpressservice.controller;

import org.example.dresstoimpressservice.dto.CreateBannerDto;
import org.example.dresstoimpressservice.model.Banner;
import org.example.dresstoimpressservice.model.Show;
import org.example.dresstoimpressservice.repository.BannerRepository;
import org.example.dresstoimpressservice.repository.ShowRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/banners")
public class BannerController {

    private final BannerRepository bannerRepository;
    private final ShowRepository showRepository;

    public BannerController(BannerRepository bannerRepository, ShowRepository showRepository) {
        this.bannerRepository = bannerRepository;
        this.showRepository = showRepository;
    }

    @GetMapping
    List<Banner> findAll() {
        return bannerRepository.findAll();
    }

    // Dodaje baner do wskazanego showId
    @PostMapping
    public ResponseEntity<?> createBanner(@RequestBody CreateBannerDto createBannerDto) {
        // Znajdź show po ID
        Optional<Show> optionalShow = showRepository.findById(createBannerDto.getShowId());

        if (optionalShow.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Show o podanym ID nie istnieje.");
        }

        Show show = optionalShow.get();

        // Sprawdzenie, czy show nie ma już przypisanego bannera
        if (show.getBanner() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Show już posiada baner.");
        }

        // Tworzenie nowego bannera
        Banner banner = new Banner();
        banner.setPhoto(createBannerDto.getPhoto());
        banner.setShow(show);

        // Zapis do bazy danych
        bannerRepository.save(banner);

        return ResponseEntity.status(HttpStatus.CREATED).body(banner);
    }
}

