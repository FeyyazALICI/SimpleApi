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
import com.example.demo.cat.repository.CatRepository;
import com.example.demo.response.ApiResponse;
import jakarta.transaction.Transactional;

// Auxilary imports
import java.util.*;


@Service
public class CatService {

    @Autowired
    private CatRepository catRepository;
    @Autowired
    private LocalValidatorFactoryBean validator;



    // BASIC GET
    // --------------------------------------------------------------------
    public List<CatEntity> getAllData() {
        return catRepository.findAll();
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
    public ResponseEntity<ApiResponse> addCat(CatEntity data) {
        String validMessage = validationStep(data);
        if(   !validMessage.equals("No Validation Error!")   ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(validMessage));
        }

        if (!(catRepository.existsByType(data.getType()))) {
            try {
                CatEntity cat = new CatEntity();
                cat.setType(data.getType());
                catRepository.saveAndFlush(cat);
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successful Operation!"));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse("Internal Server Error!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Cat Already Exists!"));
        }
    }
    // --------------------------------------------------------------------

    // BASIC DELETE
    // --------------------------------------------------------------------
    @Transactional
    public ResponseEntity<ApiResponse> deleteCat(CatEntity data) {
        String validMessage = validationStep(data);
        if(   !validMessage.equals("No Validation Error!")   ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(validMessage));
        }

        if(catRepository.existsByIdAndType(data.getId(), data.getType())){
            try {
                catRepository.delete(data);
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Succesfull Operation!"));
            } catch (Exception e) {
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
    public ResponseEntity<ApiResponse> updateCat(CatEntity data){
        String validMessage = validationStep(data);
        if(   !validMessage.equals("No Validation Error!")   ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(validMessage));
        }

        try{
            CatEntity cat = catRepository.findById(data.getId());
            cat.setType(data.getType());
            catRepository.saveAndFlush(cat);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Succesfull Operation!"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Cat wasn't found"));
        }
        
    }
    // --------------------------------------------------------------------





    // Trial of the Usage of 'Path Variable'
    public ResponseEntity<ApiResponse> helloCat(int id){
        CatEntity cat = catRepository.findById(id);
        String type= cat.getType();
        String message = "Hello "+type;
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(message));
    }
}
