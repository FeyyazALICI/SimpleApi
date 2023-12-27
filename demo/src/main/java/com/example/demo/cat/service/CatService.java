package com.example.demo.cat.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.example.demo.cat.entity.CatEntity;
import com.example.demo.cat.repository.CatRepository;
import com.example.demo.response.ApiResponse;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;

// Auxilary imports
import java.util.*;

@Service
public class CatService {

    @Autowired
    private CatRepository catRepository;
    @Autowired
    private EntityManager entityManager;

    // BASIC GET
    // --------------------------------------------------------------------
    // JPA
    public List<CatEntity> getAllData() {
        return catRepository.findAll();
    }

    // NATIVE
    public List<CatEntity> getAllDataNative() {
        String sql = "SELECT * FROM cat_types";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }
    // --------------------------------------------------------------------

    // BASIC INSERT
    // --------------------------------------------------------------------
    // JPA
    @Transactional
    public ResponseEntity<ApiResponse> addCat(CatEntity data) {
        if (!(catRepository.existsByType(data.getType()))) {
            try {
                CatEntity cat = new CatEntity();
                cat.setType(data.getType());
                catRepository.saveAndFlush(cat);
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Succesfull Operation!"));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse("Internal Server Error!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Cat Already Exists!"));
        }
    }

    // NATIVE
    @Transactional
    public ResponseEntity<ApiResponse> addCatNative(CatEntity cat) {
        List<CatEntity> cat_list = getAllData();
        String type = cat.getType();
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
    // JPA
    // --------------------------------------------------------------------
    @Transactional
    public ResponseEntity<ApiResponse> deleteCat(CatEntity data) {

        if (catRepository.existsByType(data.getType())) {
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

    // NATIVE
    @Transactional
    public ResponseEntity<ApiResponse> deleteCatNative(CatEntity data){
        List<CatEntity> cat_list = getAllData();
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
    // JPA
    @Transactional
    public ResponseEntity<ApiResponse> updateCat(CatEntity data){
        try{
            CatEntity cat = catRepository.findById(data.getId());
            cat.setType(data.getType());
            catRepository.saveAndFlush(cat);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Succesfull Operation!"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Cat wasn't found"));
        }
        
    }

    // NATIVE
    @Transactional
    public ResponseEntity<ApiResponse> updateCatNative(CatEntity data){
        List<CatEntity> cat_list = getAllData();
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
