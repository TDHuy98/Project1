package com.intern.project1.controllers;

import com.intern.project1.entities.*;
import com.intern.project1.entities.dto.*;
import com.intern.project1.constant.enums.Category;
import com.intern.project1.constant.enums.Role;
import com.intern.project1.exceptions.NotEnoughResourceException;
import com.intern.project1.exceptions.NotLoggedInException;
import com.intern.project1.exceptions.ResourceNotFoundException;
import com.intern.project1.services.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
//@AllArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserServices userServices;
    private final ProductServices productServices;
    private final OrderServices orderServices;
    private final CartServices cartServices;
    private final ShopServices shopServices;

    public UserController(UserServices userServices, ProductServices productServices, OrderServices orderServices, CartServices cartServices, ShopServices shopServices) {
        this.userServices = userServices;
        this.productServices = productServices;
        this.orderServices = orderServices;
        this.cartServices = cartServices;
        this.shopServices = shopServices;
    }

    @GetMapping("/get/products/{productName}/{pageIndex}")
    private ResponseEntity<List<ShopProductShowDto>> getProductsByName(@PathVariable String productName, @PathVariable int pageIndex) {
        if (productServices.isProductExisted(productName)) {
            return new ResponseEntity<>(productServices.getProductsByName(productName, pageIndex), HttpStatus.OK);
        } else throw new ResourceNotFoundException("This product does not exist in system");

    }

    @GetMapping("/get/product")
    private ResponseEntity<ShopProductShowDto> getProductByName(@RequestBody ShopProductShowDto shopProductShowDto) {
        if (productServices.isProductExisted(shopProductShowDto.getProductName())) {
            System.out.println(shopProductShowDto);
            return new ResponseEntity<>(productServices.getProductByName(shopProductShowDto.getShopName(),shopProductShowDto.getProductName()), HttpStatus.OK);
        } else throw new ResourceNotFoundException("This product does not exist in system");
    }
    @GetMapping("/get/product/{shopName}/{pageIndex}")
    private ResponseEntity<List<Shop_ProductDto>> getProductByShop(@PathVariable String shopName, @PathVariable int pageIndex) {
            return new ResponseEntity<>(shopServices.getShopProductsByPageIndexAndSortedBySold(shopName,pageIndex), HttpStatus.OK);
    }

    @GetMapping("/get/allProductsSortedBySold/page-{pageSelected}")
    private ResponseEntity<List<ShopProductShowDto>> getProductsSortedSold(@PathVariable int pageSelected) {

        int numbersOfProduct = productServices.getAllProductNumbers();
        int numbersOfPage = 1;
        if (numbersOfProduct % 5 > 0) {
            numbersOfPage = numbersOfProduct / 5 + 1;
        }
        if (pageSelected > numbersOfPage) {
            pageSelected = numbersOfPage;
        }
        return new ResponseEntity<>(productServices.showAllProductChosenPageSortedBySold(pageSelected), HttpStatus.OK);
    }

    @GetMapping("/get/allProductsSortedByNewest/page_{pageSelected}")
    private ResponseEntity<List<ShopProductShowDto>> getProductsSortedByNewest(@PathVariable int pageSelected) {

        int numbersOfProduct = productServices.getAllProductNumbers();
        int numbersOfPage = 1;
        if (numbersOfProduct % 5 > 0) {
            numbersOfPage = numbersOfProduct / 5 + 1;
        }
        if (pageSelected > numbersOfPage) {
            pageSelected = numbersOfPage;
        }
        return new ResponseEntity<>(productServices.showAllProductChosenPageSortedByCreatedTime(pageSelected), HttpStatus.OK);
    }

    @GetMapping("/get/allShops/page{pageSelected}")
    private ResponseEntity<List<ShopDto>> getAllShop(@PathVariable int pageSelected) {
        int numbersOfShop = shopServices.getAllShopNumbers();
        int numbersOfPage = 1;
        if (numbersOfShop % 5 > 0) {
            numbersOfPage = numbersOfShop / 5 + 1;
        } else if (pageSelected > numbersOfPage) {
            pageSelected = numbersOfPage;
        }
        return new ResponseEntity<>(shopServices.getAllShopSortedByRatingDesc(pageSelected), HttpStatus.OK);
    }

    @GetMapping("/get/shopBy{category}/page{pageSelected}")
    private ResponseEntity<List<ShopDto>> getShopByCategory(@PathVariable Category category, @PathVariable int pageSelected) {
        int numbersOfShop = shopServices.getShopNumbersOfCategory(category);
        int numbersOfPage = 1;
        if (numbersOfShop % 5 > 0) {
            numbersOfPage = numbersOfShop / 5 + 1;
        } else if (pageSelected > numbersOfPage) {
            pageSelected = numbersOfPage;
        }
        return new ResponseEntity<>(shopServices.getShopsByShopCategorySortedByRatingDesc(category, pageSelected), HttpStatus.OK);
    }

    @PatchMapping("/update/quantityOfProductInCart")
    private ResponseEntity<CartDto> changeQuantity(@Valid @RequestBody Cart_Products_ShopsDto cps) {
        return new ResponseEntity<>(cartServices.changeQuantity(cps), HttpStatus.OK);
    }

    @GetMapping("/get/shopByName/{shopName}/page_{pageSelected}")
    private ResponseEntity<List<ShopDto>> getShopByName(@PathVariable String shopName, @PathVariable int pageSelected) {
        int numbersOfShop = shopServices.getShopNumbersOfShopName(shopName);
        int numbersOfPage = 1;
        if (numbersOfShop % 5 > 0) {
            numbersOfPage = numbersOfShop / 5 + 1;
        } else if (pageSelected > numbersOfPage) {
            pageSelected = numbersOfPage;
        }
        return new ResponseEntity<>(shopServices.getShopsByShopNameSortedByRatingDesc(shopName, pageSelected), HttpStatus.OK);
    }

    @GetMapping("/get/shopByName/{shopName}")
    private ResponseEntity<ShopShowDto> getShop(@PathVariable String shopName) {
        return new ResponseEntity<>(shopServices.getShopByShopName(shopName), HttpStatus.OK);
    }

    @GetMapping("/get/shopBy-{shopAddress}/page_{pageSelected}")
    private ResponseEntity<List<ShopDto>> getShopByAddress(@PathVariable String shopAddress, @PathVariable int pageSelected) {
        int numbersOfShop = shopServices.getShopNumbersOfShopAddress(shopAddress);
        int numbersOfPage = 1;
        if (numbersOfShop % 5 > 0) {
            numbersOfPage = numbersOfShop / 5 + 1;
        } else if (pageSelected > numbersOfPage) {
            pageSelected = numbersOfPage;
        }
        return new ResponseEntity<>(shopServices.getShopsByShopNameSortedByRatingDesc(shopAddress, pageSelected), HttpStatus.OK);
    }

    @PostMapping("/add/cart")
    private ResponseEntity<CartDto> addProductToCart(@Valid @RequestBody ProductToCart product) {
        return new ResponseEntity<>(userServices.checkUserExistThenAddToCart(product),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/productFromCart")
    private ResponseEntity<Cart_Products_ShopsDto> deleteFromCart(@RequestBody Cart_Products_ShopsDto cps) {
        return new ResponseEntity<>(cartServices.deleteProductFromCart(cps), HttpStatus.OK);
    }

    @PostMapping("/check_out")
    private ResponseEntity<List<OrderDto>> checkOut(@RequestBody CartDto cartDto) {
        if (userServices.getUserByName(cartDto.getUserName()).get().getRole().equals(Role.CUSTOMER)) {
            if (orderServices.isProductQuantityEnough(cartDto)) {
                return new ResponseEntity<>(orderServices.checkOut(cartDto), HttpStatus.OK);
            } else {
                throw new NotEnoughResourceException("Shop does not have enough product's quantity");
            }
        } else throw
                new NotLoggedInException("Please sign up to continue purchase");
    }

    @GetMapping("/get/cart_{userName}")
    private ResponseEntity<CartDto> getMyCart(@PathVariable String userName) {
        return new ResponseEntity<>(cartServices.getCart(userName).get(), HttpStatus.OK);
    }

    @GetMapping("/productDataForTest")
    private ResponseEntity<List<Product>> productDataTest() {
        Product newProduct = Product.builder()
                .productName("ASUS ROG phone 5s")
                .productPrice(17000000.0)
                .productType(Category.MOBILE_GADGETS)
                .createdTime(Date.from(Instant.now()))
                .build();
        Product newProduct1 = Product.builder()
                .productName("APPLE Iphone 15")
                .productPrice(33000000.0)
                .productType(Category.MOBILE_GADGETS)
                .createdTime(Date.from(Instant.now()))
                .build();
        Product newProduct2 = Product.builder()
                .productName("APPLE MACBOOK Air 3")
                .productPrice(33000000.0)
                .productType(Category.COMPUTER_ACCESSORIES)
                .createdTime(Date.from(Instant.now()))
                .build();
        Product newProduct3 = Product.builder()
                .productName("SAMSUNG TERRACE TV")
                .productPrice(50000000.0)
                .productType(Category.CONSUMER_ELECTRONICS)
                .createdTime(Date.from(Instant.now()))
                .build();
        Product newProduct4 = Product.builder()
                .productName("COOLMATE MEN COAT")
                .productPrice(500000.0)
                .productType(Category.CLOTHES)
                .createdTime(Date.from(Instant.now()))
                .build();
        Product newProduct5 = Product.builder()
                .productName("CHEAP OFFICE DESK ")
                .productPrice(500000.0)
                .productType(Category.HOME_LIVING)
                .createdTime(Date.from(Instant.now()))
                .build();
        Product newProduct6 = Product.builder()
                .productName("GAO RANGER FIGURE")
                .productPrice(500000.0)
                .productType(Category.TOY)
                .createdTime(Date.from(Instant.now()))
                .build();
        Product newProduct7 = Product.builder()
                .productName("STEEL HAMMER")
                .productPrice(100000.0)
                .productType(Category.TOOL_HOME_IMPROVEMENT)
                .createdTime(Date.from(Instant.now()))
                .build();
        List<Product> products = new ArrayList<>();
        products.add(newProduct);
        products.add(newProduct1);
        products.add(newProduct2);
        products.add(newProduct3);
        products.add(newProduct4);
        products.add(newProduct5);
        products.add(newProduct6);
        products.add(newProduct7);
//        productRepository.save(newProduct);
//        productRepository.save(newProduct1);
//        productRepository.save(newProduct2);
//        productRepository.save(newProduct3);
//        productRepository.save(newProduct4);
//        productRepository.save(newProduct5);
//        productRepository.save(newProduct6);
//        productRepository.save(newProduct7);
        return new ResponseEntity<>(products, HttpStatus.CREATED);
    }


}
