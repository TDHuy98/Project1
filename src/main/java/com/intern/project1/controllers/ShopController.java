package com.intern.project1.controllers;

import com.intern.project1.entities.dto.*;
import com.intern.project1.exceptions.ProductNotFoundException;
import com.intern.project1.exceptions.ResourceAlreadyExistedException;
import com.intern.project1.repositories.OrderRepository;
import com.intern.project1.repositories.ShopRepository;
import com.intern.project1.services.OrderServices;
import com.intern.project1.services.ProductServices;
import com.intern.project1.services.ShopServices;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shop")
@CrossOrigin("*")
public class ShopController {
    @Autowired
    private ShopServices shopServices;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductServices productServices;
    @Autowired
    private OrderServices orderServices;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ShopRepository shopRepository;

    @GetMapping(value = {"/home", ""})
    private ResponseEntity<ShopDto> getShop(@RequestBody String userName) {
        return new ResponseEntity<>(shopServices.getShopByUserName(userName), HttpStatus.OK);
    }

    @PostMapping("/register/shop")
    private ResponseEntity<ShopDto> registerShop(@Valid @RequestBody ShopDto shopDto) {
        if (shopServices.findShopByUserName(shopDto.getUserName()).isPresent()) {
            throw new ResourceAlreadyExistedException("You have already own a shop");
        }
        var savedShop = shopServices.shopRegister(shopDto);
        return new ResponseEntity<>(savedShop, HttpStatus.CREATED);
    }

    @PutMapping("/update/shopInfo")
    private ResponseEntity<ShopInfo> updateShopInfo(@Valid @RequestBody ShopInfo shopInfo) {
        return new ResponseEntity<>(shopServices.updateShopInformation(shopInfo), HttpStatus.OK);
    }

    @PostMapping("/register/newProduct")
    private ResponseEntity<ProductDto> addNewProduct(@Valid @RequestBody ProductDto productDto) {
        if (productServices.isProductExisted(productDto.getProductName())) {
            throw new ResourceAlreadyExistedException("This Product has already existed in System, please do next step");
        } else {
            var addedProduct = productServices.addNewProduct(productDto);
            return new ResponseEntity<>(modelMapper.map(addedProduct, ProductDto.class), HttpStatus.CREATED);
        }
    }

    @PostMapping("/update/productToShop")
    private ResponseEntity<Shop_ProductDto> addToShop(@Valid @RequestBody Shop_ProductDto shopProductDto) {
        if (!productServices.isProductExisted(shopProductDto.getProductName())) {
            throw new ProductNotFoundException("Please fill product information", "/api/v1/shop/newProduct");
        } else if (shopServices.isShopHasThisProduct(shopProductDto)) {
            throw new ResourceAlreadyExistedException("Your shop has already sold this product");
        } else
            return new ResponseEntity<>(shopServices.addProductToShop(shopProductDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/productQuantity")
    private ResponseEntity<Shop_ProductDto> changeQuantity(@Valid Shop_ProductDto shopProductDto) {
        return new ResponseEntity<>(shopServices.changeProductQuantity(shopProductDto), HttpStatus.OK);
    }

    @PutMapping("/update/orderStatus")
    private ResponseEntity<OrderDetailDto> manageOrder(@Valid OrderDetailDto orderDetailDto) {
        return new ResponseEntity<>(orderServices.manageOrder(orderDetailDto), HttpStatus.OK);
    }

    @GetMapping("/get/orders")
    private ResponseEntity<List<OrderDetailDto>> getShopOrder(@RequestBody ShopDto shopDto) {
        var shop = shopRepository.findShopByShopName(shopDto.getShopName()).get();
        return new ResponseEntity<>(orderServices.getOrdersPagedByShopId(shop.getId(), 1), HttpStatus.OK);
    }

    @GetMapping("/get/ordersInPeriod-{shopName}")
    private ResponseEntity<List<OrderDetailDto>>getOrdersInPeriod(@PathVariable String shopName,@RequestBody PeriodTime periodTime){
        return new ResponseEntity<>(orderServices.getOrdersInPeriodTime(shopName,periodTime),HttpStatus.OK);
    }

    @GetMapping("/get/ordersInMonthYear-{shopName}")
    private ResponseEntity<List<OrderDetailDto>> getOrdersInMonthOfYear(@PathVariable String shopName, @RequestBody PeriodTime monthOfYear){
        return new ResponseEntity<>(orderServices.getOrdersByShopNameInMonthOfYear(shopName,monthOfYear),HttpStatus.OK);
    }
    @GetMapping("/get/incomeInMonthYear-{shopName}")
    private ResponseEntity<Double> getIncomeByMonthYear(@PathVariable String shopName,@RequestBody PeriodTime monthYear){
        return new ResponseEntity<>(shopServices.getShopIncomeByMonthYear(shopName,monthYear),HttpStatus.OK);
    }
    @GetMapping("/get/profitInMonthYear-{shopName}")
    private ResponseEntity<Double> getProfitInMonthYear(@PathVariable String shopName,@RequestBody PeriodTime monthYear){
        return new ResponseEntity<>(shopServices.getShopProfitByMonthYear(shopName,monthYear),HttpStatus.OK);
    }
}

