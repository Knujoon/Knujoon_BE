package com.example.Knujoon.service;


import com.example.Knujoon.entity.BaekjoonId;
import com.example.Knujoon.repository.BaekjoonIdRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class IdService{


    private final BaekjoonIdRepository repository;


    @Transactional
    public void insertId(){

        ArrayList<String> IdList = new ArrayList<>();
        String result = "";
        int cnt = 0;
        for (int k = 1; k <= 6; k++) {
            try{

                URL url = new URL("https://solved.ac/api/v3/ranking/in_organization?organizationId=193&page="+k);//url 가져오기
                BufferedReader br;
                br = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
                result = br.readLine();//다 읽어 내겠죠??
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                JSONArray track = (JSONArray) jsonObject.get("items");// 해당 배열을 가져오고

                for (int i = 0; i < track.size(); i++) {
                    JSONObject temp = (JSONObject) track.get(i);//i번째 객체를 가져오고

                    String handle= temp.get("handle").toString();

                    BaekjoonId temp2 = BaekjoonId.builder()
                            .handle(handle)
                            .build();

                    repository.save(temp2);
                    cnt++;
                    System.out.println(handle);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(cnt);


    }
}
