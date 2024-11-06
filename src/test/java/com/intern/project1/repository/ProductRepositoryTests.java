package com.intern.project1.repository;

import com.intern.project1.entities.Product;
import com.intern.project1.constant.enums.Category;
import com.intern.project1.repositories.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("JUnit save product operation")
    @Test
    public void givenProductObject_whenSave_thenReturnSavedProduct() {
        Product newProduct = Product.builder()
                .productName("ASUS ROG phone 5s")
                .productPrice(17000000.0)
                .productType(Category.MOBILE_GADGETS)
                .createdTime(Date.from(Instant.now()))
                .build();

        Product product = productRepository.save(newProduct);

        assertThat(product).isNotNull();
        assertThat(product.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit findAll product operation")
    @Test
    public void givenProductList_whenFindAllProduct_thenReturnProductList() {
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
        productRepository.save(newProduct);
        productRepository.save(newProduct1);
        productRepository.save(newProduct2);
        List<Product> allProduct = productRepository.findAll().stream().toList();

        assertThat(allProduct).isNotNull();
        assertThat(allProduct.size()).isEqualTo(3);
    }

    @DisplayName("JUnit findAllOrderByCreatedTimeDesc product operation")
    @Test
    public void givenProductList_whenFindAllOrderByCreatedTimeDesc_thenReturnProductList() {
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
        productRepository.save(newProduct);
        productRepository.save(newProduct1);
        productRepository.save(newProduct2);
        List<Product> allProduct = productRepository.findAllOrderByCreatedTimeDesc().stream().toList();

        assertThat(allProduct).isNotNull();
        assertThat(allProduct.size()).isEqualTo(3);
    }

    @DisplayName("JUnit findAllOrderBySoldDesc product operation")
    @Test
    public void givenProductList_whenFindAllOrderBySoldDesc_thenReturnProductList() {
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
        productRepository.save(newProduct);
        productRepository.save(newProduct1);
        productRepository.save(newProduct2);
        List<Product> allProduct = productRepository.findAllOrderBySoldDesc().stream().toList();

        assertThat(allProduct).isNotNull();
        assertThat(allProduct.size()).isEqualTo(3);
    }

    @DisplayName("JUnit findProductByName operation")
    @Test
    public void givenProductList_whenFindProductName_thenReturnProduct() {
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
        productRepository.save(newProduct);
        productRepository.save(newProduct1);
        productRepository.save(newProduct2);
        Optional<Product> product = productRepository.findByProductName("ASUS ROG phone 5s");

//        assertThat(product).isNotNull();
        assertThat(product).isPresent();
        assertThat(product.get().getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit FindProductsByProductNameOrderByCreatedTimeDesc operation")
    @Test
    public void givenProductList_whenFindProductsByProductNameOrderByCreatedTimeDesc_thenReturnProductList() {
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
        productRepository.save(newProduct);
        productRepository.save(newProduct1);
        productRepository.save(newProduct2);
        List<Product> productList = productRepository.findProductsByNameOrderByCreatedTimeDesc("APPLE").stream().toList();

        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit FindAllProductByProductNameOrderBySoldDesc operation")
    @Test
    public void givenProductList_whenFindAllProductByProductNameOrderBySoldDesc_thenReturnProductList() {
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
        productRepository.save(newProduct);
        productRepository.save(newProduct1);
        productRepository.save(newProduct2);
        List<Product> productList = productRepository.findProductsByNameOrderBySoldDesc("APPLE").stream().toList();

        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(2);
    }


    @DisplayName("JUnit findProductsByCategorySortedBySoldDesc operation")
    @Test
    public void givenHOME_LIVINGCategory_whenFindByHOME_LIVINGOrderBySoldDesc_thenReturnProductList() {
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


        productRepository.save(newProduct);
        productRepository.save(newProduct1);
        productRepository.save(newProduct2);
        productRepository.save(newProduct3);
        productRepository.save(newProduct4);
        productRepository.save(newProduct5);
        productRepository.save(newProduct6);
        productRepository.save(newProduct7);
        List<Product> productList = productRepository.findProductsByCategorySortedBySoldDesc(Category.HOME_LIVING).stream().toList();

        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(1);
    }
    @DisplayName("JUnit findProductsByCategorySortedByCreatedTimeDesc operation")
    @Test
    public void givenHOME_LIVINGCategory_whenFindByHOME_LIVINGOrderByCreatedTimeDesc_thenReturnProductList() {
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


        productRepository.save(newProduct);
        productRepository.save(newProduct1);
        productRepository.save(newProduct2);
        productRepository.save(newProduct3);
        productRepository.save(newProduct4);
        productRepository.save(newProduct5);
        productRepository.save(newProduct6);
        productRepository.save(newProduct7);
        List<Product> productList = productRepository.findProductsByCategorySortedByCreatedTimeDesc(Category.HOME_LIVING).stream().toList();

        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(1);
    }

}
