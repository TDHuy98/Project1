package com.intern.project1.controllers;

import com.intern.project1.entities.dto.ProductDto;
import com.intern.project1.entities.dto.ShopProductShowDto;
import com.intern.project1.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/home")
@RestController
@CrossOrigin("*")
public class HomeController {
    @Autowired
    private ProductServices productServices;

    @GetMapping("")
    private ResponseEntity<List<ShopProductShowDto>> getProductsSortedSold() {
        return new ResponseEntity<>(productServices.showAllProductChosenPageSortedBySold(1), HttpStatus.OK);
    }
}
