package com.example.Knujoon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length =50, nullable = false)
    private String userId;//백준 아이디를 저장하는 필드

    @Column(length =50, nullable = false)
    private String email;

    @Column(length =100, nullable = false)
    private String password;

    @Column(length =100, nullable = true)
    private String profile_url;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder

    public Member(String userId, String email, String password, String profile_url, Authority authority) {//멤버 빌더
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.profile_url = profile_url;
        this.authority = authority;
    }
}