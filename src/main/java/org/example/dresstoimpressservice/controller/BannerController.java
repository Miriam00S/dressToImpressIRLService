package org.example.dresstoimpressservice.controller;

import org.example.dresstoimpressservice.model.Banner;
import org.example.dresstoimpressservice.model.User;
import org.example.dresstoimpressservice.repository.BannerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/banners")
public class BannerController {

    private final BannerRepository bannerRepository;

    public BannerController(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @GetMapping
    List<Banner> findAll() {
        return bannerRepository.findAll();
    }
}

