package com.example.Knujoon.dto;

import com.example.Knujoon.entity.Authority;
import com.example.Knujoon.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String userId;
    private String email;
    private String password;
    //회원가입을 할 때 사용이 되는 친구죠?
    public Member toMember(PasswordEncoder passwordEncoder,String profile_url) {
        return Member.builder()
                .userId(userId)
                .email(email)
                .password(passwordEncoder.encode(password))
                .profile_url(profile_url)
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userId, password);
    }
}