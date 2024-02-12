package com.example.Knujoon.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ids {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//고유 아이디..

    @Column
    private String id2;



}
