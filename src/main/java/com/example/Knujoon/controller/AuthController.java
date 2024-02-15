package com.example.Knujoon.controller;

import com.example.Knujoon.dto.*;
import com.example.Knujoon.service.AuthService;
import com.example.Knujoon.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return authService.signup(memberRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        System.out.println("ㅗ");
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("email/send")
    public ResponseDto<?> sendEmail(@RequestParam("email") String email) throws Exception {
        emailService.sendSimpleMessage(email);
        return ResponseDto.success("이메일 전송 완료");
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {

        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @GetMapping("/valid_id")//백준 아이디의 중복을 검사해줌요
    public ResponseDto<?> confirmId(@RequestBody UserIdRequestDto requestDto){
        return authService.confirmId(requestDto);
    }



}