package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.service.common.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@RequestMapping
public class LocationRestController {

    private final LocationService locationService;


}
