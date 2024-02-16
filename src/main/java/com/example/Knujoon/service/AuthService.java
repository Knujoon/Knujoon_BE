package com.example.Knujoon.service;

import com.example.Knujoon.dto.*;
import com.example.Knujoon.entity.Member;
import com.example.Knujoon.entity.RefreshToken;
import com.example.Knujoon.jwt.TokenProvider;
import com.example.Knujoon.repository.BaekjoonIdRepository;
import com.example.Knujoon.repository.MemberRepository;
import com.example.Knujoon.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BaekjoonIdRepository baekjoonIdRepository;
    private final IdService idService;
    private final RedisService redisService;


    @Transactional
    public ResponseDto<?> confirmId(String userId) {
        if (baekjoonIdRepository.existsByUserId(userId)) {
            return ResponseDto.fail("duplicated_id", "이 아이디는 중복되었습니다.");
        } else
            return ResponseDto.success("회원가입이 가능한 아이디 입니다.");


    }

    @Transactional
    public ResponseDto<?> signup(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            return ResponseDto.fail("duplicated_user", "이미 가입이 되어 있는 유저입니다.");
            //throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        String profileUrl = idService.getProfileUrl(memberRequestDto.getUserId());
        //profileurl을 불러와서 매개변수로 넘겨 봅시다.
        Member member = memberRequestDto.toMember(passwordEncoder, profileUrl);
        memberRepository.save(member);
        return ResponseDto.success("회원가입 성공");
    }

    @Transactional
    public TokenDto login(MemberRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();
        System.out.println(authenticationToken.getName());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public ResponseDto<?> verificationsEmail(String email, String code) {

        String findValue = redisService.getValues(email);

        System.out.println(findValue);
        //일단 이메일을 보내니깐 무조건 값은 가지고 있음
        //이제 값을 찾았는데.
        if (!findValue.equals("false") && !findValue.equals(code)) {//값은 찾았으나 코드가 다를 때.
            return ResponseDto.fail("wrong value", "일치하지 않은 코드 입니다");

        } else if (!findValue.equals("false") && findValue.equals(code)) {
            return ResponseDto.success("인증이 완료되었습니다.");
        } else
            return ResponseDto.fail("time elapsed", "입력 시간이 초과되었습니다.");


    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}