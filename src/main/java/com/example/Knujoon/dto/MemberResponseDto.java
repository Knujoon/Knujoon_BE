package com.example.Knujoon.dto;


import com.example.Knujoon.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String userId;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getUserId());
    }
}