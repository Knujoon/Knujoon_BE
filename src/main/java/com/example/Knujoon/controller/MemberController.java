package com.example.Knujoon.controller;

import com.example.Knujoon.request.MemberRequestDto;
import com.example.Knujoon.request.RequestLoginDto;
import com.example.Knujoon.response.ResponseTokenDto;
import com.example.Knujoon.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseTokenDto login(@RequestBody RequestLoginDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        ResponseTokenDto tokenDto = memberService.login(memberId, password);
        return tokenDto;
    }

    @PostMapping("/test")
    public String test() {
        return "sucess";
    }
}