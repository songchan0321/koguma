package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.service.product.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewRestContoller {

    private final ReviewService reviewService;


}
