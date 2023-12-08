package com.fiveguys.koguma.controller;


import com.fiveguys.koguma.service.common.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageRestController {

    private final ImageService imageService;

//    @GetMapping("/{id}")
//
//
//    @PostMapping("/new")
}
