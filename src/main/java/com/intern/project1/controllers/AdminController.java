package com.intern.project1.controllers;

import com.intern.project1.entities.Shop;
import com.intern.project1.entities.dto.ShopDto;
import com.intern.project1.entities.dto.ShopManagement;
import com.intern.project1.services.ShopServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin("*")
public class AdminController {
    @Autowired
    private ShopServices shopServices;


    @GetMapping(value = {"/home/{pageSelected}", "/{pageSelected}"})
    private ResponseEntity<List<ShopManagement>> getShopList(@PathVariable int pageSelected) {
        var shopList = shopServices.getAllShop(pageSelected);
        return new ResponseEntity<>(shopList, HttpStatus.OK);
    }

    @PutMapping("/approve/openingRequest")
    private ResponseEntity<ShopManagement> approveShopRequest(@RequestBody ShopManagement shopManagement) {
        return new ResponseEntity<>(shopServices.approveShopRequest(shopManagement), HttpStatus.OK);
    }

    @PutMapping("/set/profitFee")
    private HttpStatus setProfitFee() {
        shopServices.setProfitFee();
        return HttpStatus.OK;
    }

    @PutMapping("/change/shopStatus")
    private ResponseEntity<ShopManagement> changeShopStatus(@RequestBody ShopManagement shopManagement) {
        return new ResponseEntity<>(shopServices.changeShopStatus(shopManagement), HttpStatus.OK);
    }

    @GetMapping("/get/shop-{shopName}")
    private ResponseEntity<ShopManagement> getShop(@PathVariable String shopName) {
        return new ResponseEntity<>(shopServices.getShopByShopNameForAdmin(shopName), HttpStatus.OK);
    }

    @GetMapping("/get/notApproved")
    private ResponseEntity<List<ShopManagement>> getNotApprovedShop(){
        return new ResponseEntity<>(shopServices.getNotApprovedShop(),HttpStatus.OK);
    }
}
