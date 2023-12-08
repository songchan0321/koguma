package com.fiveguys.koguma.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get() {
        return "GGGGG";
    }
}
