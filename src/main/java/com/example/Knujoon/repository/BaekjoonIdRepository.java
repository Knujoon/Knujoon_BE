package com.example.Knujoon.repository;

import com.example.Knujoon.entity.BaekjoonId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaekjoonIdRepository extends JpaRepository<BaekjoonId,Long> {

    boolean existsByUserId(String userId);


}
