package com.example.Knujoon.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaekjoonId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//고유 아이디..

    @Column//아이디 저장
    private String userId;





}
