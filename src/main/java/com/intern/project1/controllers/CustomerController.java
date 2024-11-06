package com.intern.project1.controllers;

import com.intern.project1.constant.uri.URI;
import com.intern.project1.entities.ProductRating;
import com.intern.project1.entities.dto.OrderDetailDto;
import com.intern.project1.entities.dto.ProductRatingDto;
import com.intern.project1.entities.dto.ShopRatingDto;
import com.intern.project1.exceptions.NotCorrectTypeOfRequest;
import com.intern.project1.services.OrderServices;
import com.intern.project1.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin("*")
public class CustomerController {
    @Autowired
    private OrderServices orderServices;
    @Autowired
    private UserServices userServices;

    @GetMapping("/get/order/{userName}/{index}")
    private ResponseEntity<List<OrderDetailDto>> getOrders(@PathVariable String userName, @PathVariable int index) {
        return new ResponseEntity<>(orderServices.getCustomerOrder(userName, index), HttpStatus.OK);
    }


    @RequestMapping(value = "/rate/product", method = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
    private ResponseEntity<ProductRatingDto> rateProduct(@RequestBody ProductRatingDto productRatingDto) {
        Optional<ProductRating> beingRatedProduct = userServices.getRatedProduct(productRatingDto.getShopName(),
                productRatingDto.getUserName(),
                productRatingDto.getProductName());
        if (beingRatedProduct.isPresent()) {
            if (beingRatedProduct.get().getRating().equals(productRatingDto.getRating())) {
                throw new NotCorrectTypeOfRequest(
                        "You have already rated this product, rating the same result will unrated",
                        URI.REMOVE_RATE_PRODUCT_URI);
            } else {
                throw new NotCorrectTypeOfRequest(
                        "You are re-rating this product",
                        URI.UPDATE_RATE_PRODUCT_URI);
            }
        } else {
            return new ResponseEntity<>(userServices.rateProduct(productRatingDto), HttpStatus.OK);
        }
    }

    @PutMapping(value = ("/update/rate/product"))
    private ResponseEntity<ProductRatingDto> updateRateProduct(@RequestBody ProductRatingDto productRatingDto) {
        return new ResponseEntity<>(userServices.updateRateProduct(productRatingDto), HttpStatus.OK);
    }

    @DeleteMapping(value = ("/remove/rate/product"))
    private HttpStatus removeRateProduct(@RequestBody ProductRatingDto productRatingDto) {
        userServices.removeRateProduct(productRatingDto);
        return HttpStatus.OK;
    }

    @PostMapping(value = "/rate/shop")
    private ResponseEntity<ShopRatingDto> rateShop(@RequestBody ShopRatingDto shopRatingDto) {
        var beingRatedShop = userServices.getRatedShop(shopRatingDto.getShopName(), shopRatingDto.getUserName());
        if (beingRatedShop.isPresent()) {
            if (beingRatedShop.get().getRating().equals(shopRatingDto.getRating())) {
                throw new NotCorrectTypeOfRequest(
                        "You have already rated this shop, rating the same result will unrated",
                        URI.REMOVE_RATE_SHOP_URI
                );
            } else {
                throw new NotCorrectTypeOfRequest(
                        "You are re-rating this shop",
                        URI.UPDATE_RATE_SHOP_URI
                );
            }
        } else {
            return new ResponseEntity<>(userServices.rateShop(shopRatingDto), HttpStatus.OK);
        }
    }

    @PutMapping(value = ("/rate/update/shop"))
    private ResponseEntity<ShopRatingDto> updateRateShop(@RequestBody ShopRatingDto shopRatingDto) {
        ShopRatingDto updated = userServices.updateRateShop(shopRatingDto);
//        userServices.updateRatingShop(shopRatingDto.getShopName());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping(value = ("/rate/remove/shop"))
    private HttpStatus removeRateShop(@RequestBody ShopRatingDto shopRatingDto) {
        userServices.removeRateShop(shopRatingDto);
//        userServices.updateRatingShop(shopRatingDto.getShopName());
        return HttpStatus.OK;
    }
}
