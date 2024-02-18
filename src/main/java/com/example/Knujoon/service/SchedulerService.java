package com.example.Knujoon.service;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class SchedulerService {
    private final IdService idService;

    @Scheduled(cron = "0 0 0 * * *" , zone = "Asia/Seoul")//초, 분, 시, 일, 월, 요일
    //매일 00시에 강원대학교 그룹에 속혀있는 아이디를 새로 저장함
    public void insertId() {
        idService.insertId();
    }
}
