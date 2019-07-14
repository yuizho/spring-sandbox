package io.github.yuizho.springsandbox.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/timestamp")
public class TimestampController {
    @GetMapping("")
    public String get() {
        return LocalDateTime.now().toString();
    }
}
