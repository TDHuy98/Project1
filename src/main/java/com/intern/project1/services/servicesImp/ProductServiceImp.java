package com.intern.project1.services.servicesImp;

import com.intern.project1.entities.Product;
import com.intern.project1.entities.Shop_Product;
import com.intern.project1.entities.dto.ProductDto;
import com.intern.project1.entities.dto.ShopProductShowDto;
import com.intern.project1.constant.enums.Category;
import com.intern.project1.repositories.ProductRatingRepository;
import com.intern.project1.repositories.ProductRepository;
import com.intern.project1.repositories.Shop_ProductRepository;
import com.intern.project1.services.ProductServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImp implements ProductServices {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRatingRepository productRatingRepository;
    @Autowired
    private Shop_ProductRepository shop_ProductRepository;

    @Override
    public boolean isProductExisted(String productName) {
        return productRepository.findByProductName(productName).isPresent();
    }

    @Override
    public Product addNewProduct(ProductDto productDto) {
        Product newProduct = modelMapper.map(productDto, Product.class);
        newProduct.setCreatedTime(Date.from(Instant.now()));
        newProduct.setRating(0.0);
        newProduct.setSold(0L);
        return productRepository.save(newProduct);
    }


    @Override
    public List<ShopProductShowDto> showAllProductChosenPageSortedByCreatedTime(int pageSelection) {

        Pageable pageIndex = PageRequest.of(pageSelection - 1, 5, Sort.by("createdTime").descending());
        return shop_ProductRepository.findAll(pageIndex)
                .stream().map(this::mapShopProductToShowDto)
                .toList();
    }

    @Override
    public List<ShopProductShowDto> showAllProductChosenPageSortedBySold(int pageSelection) {
        Pageable pageIndex = PageRequest.of(pageSelection - 1, 5, Sort.by("sold").descending());
        return shop_ProductRepository.findAll(pageIndex)
                .stream().map(this::mapShopProductToShowDto)
                .toList();
    }
private ShopProductShowDto mapShopProductToShowDto(Shop_Product shopProduct){
        return ShopProductShowDto.builder()
                .productName(shopProduct.getProduct().getProductName())
                .productPrice(shopProduct.getProduct().getProductPrice())
                .shopName(shopProduct.getShop().getShopName())
                .sold(shopProduct.getSold())
                .createdTime(shopProduct.getCreatedTime())
                .updatedTime(shopProduct.getUpdatedTime())
                .build();
}
    @Override
    public List<ShopProductShowDto> getProductsByName(String productName, int pageIndex) {
        Pageable pageable=PageRequest.of(pageIndex-1,10);
        return shop_ProductRepository.findByProductNameOrderBySoldDesc(productName, pageable)
                .stream()
                .map(this::mapShopProductToShowDto)
                .toList();

    }
    @Override
    public ShopProductShowDto getProductByName(String shopName, String productName) {
        return mapShopProductToShowDto(shop_ProductRepository.findByProductNameAndShopName(productName,shopName).get());

    }

    @Override
    public int getAllProductNumbers() {
        return productRepository.findAll().size();
    }

    @Override
    public List<ProductDto> searchProductsByProductNamePagedSortedBySold(String productName, int pageSelection) {
        Pageable pageIndex = PageRequest.of(pageSelection - 1, 5, Sort.by("sold").descending());
        return productRepository.findProductsByNameOrderBySoldDesc(productName, pageIndex)
                .stream().map((element) -> modelMapper.map(element, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchProductsByProductNamePagedSortedByCreatedTime(String productName, int pageSelection) {
        Pageable pageIndex = PageRequest.of(pageSelection - 1, 5, Sort.by("createdTime").descending());
        return productRepository.findProductsByNameOrderByCreatedTimeDesc(productName, pageIndex)
                .stream().map((element) -> modelMapper.map(element, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategorySortedByCreatedTimeDesc(Category category, Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        return productRepository.findProductsByCategorySortedByCreatedTimeDesc(category, pageable)
                .stream().map((element) -> modelMapper.map(element, ProductDto.class))
                .collect(Collectors.toList());
    }



}
