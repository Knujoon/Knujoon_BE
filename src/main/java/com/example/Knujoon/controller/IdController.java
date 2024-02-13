package com.example.Knujoon.controller;


import com.example.Knujoon.service.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequiredArgsConstructor
public class IdController {

    private final IdService idService;

    @PutMapping("/users/id")
    public String test() {
        idService.insertId();
        return "good";
    }



}
