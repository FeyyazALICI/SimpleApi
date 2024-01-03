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
    
    /**
    * Last Update on 31.12.2023
    *
    * @author Feyyaz ALICI
    */

    @Id
    private int id;

    @NotNull(message="Type can not be left empty!")
    @NotBlank(message="Type can not be left empty!")
    private String type;


}


    /* For Validation
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    * For Swagger       // SwaggerConfig.java is needed         // http://localhost:8080/swagger-ui/index.html
    <dependency>
	    <groupId>org.springdoc</groupId>
	    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
	    <version>2.3.0</version>
    </dependency>
     */
