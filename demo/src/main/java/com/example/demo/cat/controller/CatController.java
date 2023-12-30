package com.example.demo.cat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.cat.entity.CatEntity;
import com.example.demo.cat.service.CatService;
import com.example.demo.response.ApiResponse;

@RestController
@RequestMapping("/cat")
public class CatController {
    
    @Autowired
    private CatService catService;

    // GET
    //-------------------------------------------------------------------------
    // JPA
    @GetMapping("/allData")
    public Iterable<CatEntity> getAllData(){
        return catService.getAllData();
    }

    // NATIVE
    @GetMapping("/allDataNative")
    public Iterable<CatEntity> getAllDataNative(){
        return catService.getAllDataNative();
    }
    //-------------------------------------------------------------------------



    // INSERT
    //-------------------------------------------------------------------------
    // JPA
    @PostMapping("/addCat")
    public ResponseEntity<ApiResponse> addCat(@RequestBody CatEntity cat){
        return catService.addCat(cat);
    }

    //NATIVE
    @PostMapping("/addCatNative")
    public ResponseEntity<ApiResponse> addCatNative(@RequestBody CatEntity cat){
        return catService.addCat(cat);
    }
    //-------------------------------------------------------------------------


    // DELETE
    //-------------------------------------------------------------------------
    // JPA
    @PostMapping("/deleteCat")
    public ResponseEntity<ApiResponse> deleteCat(@RequestBody CatEntity cat){
        return catService.deleteCat(cat);
    }

    //NATIVE
    @PostMapping("/deleteCatNative")
    public ResponseEntity<ApiResponse> deleteCatNative(@RequestBody CatEntity cat){
        return catService.deleteCatNative(cat);
    }
    //-------------------------------------------------------------------------


    // UPDATE
    //-------------------------------------------------------------------------
    // JPA
    @PostMapping("/updateCat")
    public ResponseEntity<ApiResponse> updateCat(@RequestBody CatEntity cat){
        return catService.updateCat(cat);
    }

    //NATIVE
    @PostMapping("/updateCatNative")
    public ResponseEntity<ApiResponse> updateCatNative(@RequestBody CatEntity cat){
        return catService.updateCatNative(cat);
    }
    //-------------------------------------------------------------------------







    // Trial of the Usage of 'Path Variable'  
    @PostMapping("/helloCat/{id}")
    public ResponseEntity<ApiResponse> helloCat(@PathVariable int id){
        return catService.helloCat(id);
    }
}
