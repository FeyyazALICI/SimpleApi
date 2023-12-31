package com.example.demo.cat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @GetMapping
    public Iterable<CatEntity> getAllData(){
        return catService.getAllData();
    }
    //-------------------------------------------------------------------------

    // INSERT
    //-------------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<ApiResponse> addCat(@RequestBody CatEntity cat){
        return catService.addCat(cat);
    }
    //-------------------------------------------------------------------------

    // DELETE
    //-------------------------------------------------------------------------
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteCat(@RequestBody CatEntity cat){
        return catService.deleteCat(cat);
    }
    //-------------------------------------------------------------------------

    // UPDATE
    //-------------------------------------------------------------------------
    @PutMapping
    public ResponseEntity<ApiResponse> updateCat(@RequestBody CatEntity cat){
        return catService.updateCat(cat);
    }
    //-------------------------------------------------------------------------



    // Trial of the Usage of 'Path Variable'  
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse> helloCat(@PathVariable int id){
        return catService.helloCat(id);
    }
}
