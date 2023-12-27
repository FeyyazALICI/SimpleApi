package com.example.demo.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.cat.entity.CatEntity;
import java.util.List;


public interface CatRepository extends JpaRepository<CatEntity, Integer>{
    

    
    boolean existsByType(String type);

    CatEntity findById(int id);

    

}
