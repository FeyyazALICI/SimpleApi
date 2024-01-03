package com.example.demo.cat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.cat.entity.CatEntity;
import com.example.demo.cat.service.CatServiceNative;
import com.example.demo.response.ApiResponse;

@RestController
@RequestMapping("/catNative")
public class CatControllerNative {
    
    @Autowired
    private CatServiceNative catServiceNative;

    // GET
    //-------------------------------------------------------------------------
    @GetMapping
    public Iterable<CatEntity> getAllDataNative(){
        return catServiceNative.getAllDataNative();
    }
    //-------------------------------------------------------------------------

    // INSERT
    //-------------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<ApiResponse> addCatNative(@RequestBody CatEntity cat){
        return catServiceNative.addCatNative(cat);
    }
    //-------------------------------------------------------------------------

    // DELETE
    //-------------------------------------------------------------------------
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteCatNative(@RequestBody CatEntity cat){
        return catServiceNative.deleteCatNative(cat);
    }
    //-------------------------------------------------------------------------
    
    // UPDATE
    //-------------------------------------------------------------------------
    @PutMapping
    public ResponseEntity<ApiResponse> updateCatNative(@RequestBody CatEntity cat){
        return catServiceNative.updateCatNative(cat);
    }
    //-------------------------------------------------------------------------
}
