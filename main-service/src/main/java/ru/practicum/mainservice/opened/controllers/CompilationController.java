package ru.practicum.mainservice.opened.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compilations")
public class CompilationController {

    // Получение подборок событий
    @GetMapping
    public void getCompilations(
            @RequestParam boolean pined,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {

    }
}
