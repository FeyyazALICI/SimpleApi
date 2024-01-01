package com.example.demo.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.cat.entity.CatEntity;



public interface CatRepository extends JpaRepository<CatEntity, Integer>{
    

    
    boolean existsByType(String type);

    CatEntity findById(int id);

    boolean existsById(int id);

    boolean existsByIdAndType(int id, String type);
}
