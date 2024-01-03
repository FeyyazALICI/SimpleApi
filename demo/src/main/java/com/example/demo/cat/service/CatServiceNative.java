package com.example.demo.cat.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.example.demo.cat.entity.CatEntity;
import com.example.demo.response.ApiResponse;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;

// Auxilary imports
import java.util.*;


@Service
public class CatServiceNative {
    
    @Autowired
    private LocalValidatorFactoryBean validator;
    @Autowired
    private EntityManager entityManager;



    // BASIC GET
    // --------------------------------------------------------------------
    public List<CatEntity> getAllDataNative() {
        String sql = "SELECT * FROM cat_types";
        Query query = entityManager.createNativeQuery(sql, CatEntity.class);
        return query.getResultList();
    }
    // --------------------------------------------------------------------

    // Validation Handling
    // --------------------------------------------------------------------
    public String validationStep(CatEntity data) {
        // Validation error handling
        Errors errors = new BeanPropertyBindingResult(data, "catEntity");
        ValidationUtils.invokeValidator(validator, data, errors);       // validating entity constrainsts

        if (errors.hasErrors()) {
            String errorMessage = errors.getFieldError().getDefaultMessage();
            return "Validation Error: " + errorMessage;
        } else {
            return "No Validation Error!";
        }
    }
    // --------------------------------------------------------------------

    // BASIC INSERT
    // --------------------------------------------------------------------
    @Transactional
    public ResponseEntity<ApiResponse> addCatNative(CatEntity data) {
        String validMessage = validationStep(data);
        if(   !validMessage.equals("No Validation Error!")   ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(validMessage));
        }

        List<CatEntity> cat_list = getAllDataNative();
        String type = data.getType();
        boolean is_the_cat_exist = false;
        for (CatEntity item : cat_list) {
            if (item.getType().equals(type)) {
                is_the_cat_exist = true;
            }
        }
        if (is_the_cat_exist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Cat Already Exists!"));
        } else {
            try {
                String sql = "INSERT INTO cat_types (type) VALUES (:type)";
                Query query = entityManager.createNativeQuery(sql);
                query.setParameter("type", type);
                int result = query.executeUpdate();
                if (result > 0) {
                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Succesfull Operation!"));
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ApiResponse("Internal Server Error!"));
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Bad Request!"));
            }
        }
    }
    // --------------------------------------------------------------------

    // BASIC DELETE
    // --------------------------------------------------------------------
    @Transactional
    public ResponseEntity<ApiResponse> deleteCatNative(CatEntity data){
        String validMessage = validationStep(data);
        if(   !validMessage.equals("No Validation Error!")   ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(validMessage));
        }

        List<CatEntity> cat_list = getAllDataNative();
        int id = data.getId();
        String type = data.getType();
        boolean is_the_cat_exist = false;
        for(CatEntity item: cat_list){
            if(   item.getType().equals(type) && (item.getId() == id)  ){
                is_the_cat_exist = true;
            }
        }
        if(is_the_cat_exist){
            try{
                String sql ="DELETE FROM cat_types WHERE TYPE = :type";
                Query query = entityManager.createNativeQuery(sql);
                query.setParameter("type", type);
                int result = query.executeUpdate();
                if(result>0){
                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Succesfull Operation!"));
                }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ApiResponse("Internal Server Error!"));      
            }catch(Exception e){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ApiResponse("Internal Server Error!"));                   
            }
 
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Cat wasn't found"));
    }
    // --------------------------------------------------------------------

    // BASIC UPDATE
    // --------------------------------------------------------------------
    @Transactional
    public ResponseEntity<ApiResponse> updateCatNative(CatEntity data){
        String validMessage = validationStep(data);
        if(   !validMessage.equals("No Validation Error!")   ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(validMessage));
        }

        List<CatEntity> cat_list = getAllDataNative();
        int id = data.getId();
        String type = data.getType();
        boolean is_the_cat_exist = false;
        System.out.println(id);
        for(CatEntity item: cat_list){
            if(   (item.getId() == id)  ){
                is_the_cat_exist = true;
            }
        }
        System.out.println(is_the_cat_exist);
        if(is_the_cat_exist){
            try{
                String sql = "UPDATE cat_types        SET TYPE = :type                        WHERE id = :id";
                Query query = entityManager.createNativeQuery(sql);
                query.setParameter("type", type);
                query.setParameter("id", id);
                System.out.println("before int");
                int result = query.executeUpdate();
                System.out.println("result is " + result);
                if(result>0){
                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Succesfull Operation!"));
                }
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal Server Error!"));  

            }catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal Server Error2!"));  
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Cat wasn't found"));
    }
    // --------------------------------------------------------------------
}
