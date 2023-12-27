package com.example.demo.cat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "cat_types")
@Data
@Entity
public class CatEntity {
    

    @Id
    private int id;
    private String type;


}
