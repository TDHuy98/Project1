package com.intern.project1.services;

import com.intern.project1.entities.Product;
import com.intern.project1.entities.dto.ProductDto;
import com.intern.project1.entities.dto.ShopProductShowDto;
import com.intern.project1.constant.enums.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductServices {
    boolean isProductExisted(String productName);

    Product addNewProduct(ProductDto productDto);

    List<ShopProductShowDto> showAllProductChosenPageSortedByCreatedTime(int pageSelection);

    ShopProductShowDto getProductByName(String shopName, String productName);

    int getAllProductNumbers();

    List<ProductDto> searchProductsByProductNamePagedSortedBySold(String productName, int pageSelection);

    List<ProductDto> searchProductsByProductNamePagedSortedByCreatedTime(String productName, int pageSelection);

    List<ProductDto> getProductsByCategorySortedByCreatedTimeDesc(Category category, Integer pageIndex);

    List<ShopProductShowDto> showAllProductChosenPageSortedBySold(int pageSelection);

    List<ShopProductShowDto> getProductsByName(String productName, int pageIndex);
}
