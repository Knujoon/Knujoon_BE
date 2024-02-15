package com.example.Knujoon.controller;


import com.example.Knujoon.dto.ResponseDto;
import com.example.Knujoon.service.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequiredArgsConstructor
public class IdController {

    private final IdService idService;
    ///

    @PostMapping("/users/id")
    public ResponseDto<?> test() {
        idService.insertId();
        return ResponseDto.success("good");
    }

//    @GetMapping("/cicd/test")
//    public String cicd() {
//        System.out.println("hi");
//        return "good";
//    }



}
