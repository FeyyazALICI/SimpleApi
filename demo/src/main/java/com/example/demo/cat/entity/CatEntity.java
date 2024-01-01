package com.example.demo.cat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Table(name = "cat_types")
@Data
@Entity
public class CatEntity {
    
    @Id
    private int id;

    @NotNull
    @NotBlank
    private String type;


}




    /* For Validation
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
     */
