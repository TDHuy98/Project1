package com.intern.project1.services.servicesImp;


import com.intern.project1.entities.*;
import com.intern.project1.entities.dto.*;
import com.intern.project1.constant.enums.Rating;
import com.intern.project1.constant.enums.Role;
import com.intern.project1.repositories.*;
import com.intern.project1.services.CartServices;
import com.intern.project1.services.UserServices;
import com.intern.project1.services.authentication.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServicesImp implements UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartServices cartServices;
    @Autowired
    private AuthenticationService authService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRatingRepository productRatingRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopRatingRepository shopRatingRepository;

    @Override
    public List<ProductDto> defaultPage() {
        return productRepository.findAll().stream().map((element) -> modelMapper.map(element, ProductDto.class)).toList();
    }

    @Override
    public UserDto createNewUser(UserDto userDto) {
        User newUser = modelMapper.map(userDto, User.class);
        newUser.setCreatedTime(Date.from(Instant.now()));
        userRepository.save(newUser);
        return userDto;
    }

    @Override
    public List<OrderDto> getOrdersByUserIdPagedSortedByCreatedTime(Long userId, Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        return orderRepository.findOrdersByUserIdSortedByCreatedTime(userId, pageable)
                .stream().map((element) -> modelMapper.map(element, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public CartDto checkUserExistThenAddToCart(ProductToCart product) {
        //check xem neu co ton tai nguoi dung trong database thi thuc hien them product vao cart binh thuong
        if (userRepository.findByUserName(product.getUserName()).isPresent()) {
            return cartServices.addProductToCart(product);
        }
        //Neu khong ton tai user trong database thi thuc hien tao mot user voi role guest va gio hang tuong ung
        else {
            RegisterRequest newGuest = RegisterRequest.builder()
                    .userName(String.valueOf(new RandomString(12)))
                    .password(String.valueOf(new RandomString(6)))
                    .role(Role.GUEST)
                    .build();
            var savedGuest = modelMapper.map(newGuest, User.class);
            savedGuest.setCreatedTime(Date.from(Instant.now()));
            userRepository.save(savedGuest);
            cartServices.initCart(savedGuest.getId());
            product.setUserName(newGuest.getUserName());
            return cartServices.addProductToCart(product);
        }
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public ProductRatingDto rateProduct(ProductRatingDto productRatingDto) {
        User ratingUser = userRepository.findByUserName(productRatingDto.getUserName()).get();
        Product beingRatedProduct = productRepository.findByProductName(productRatingDto.getProductName()).get();
        Shop inShop = shopRepository.findShopByShopName(productRatingDto.getShopName()).get();
        Optional<ProductRating> rated = productRatingRepository.findByProductNameUserNameShopName(productRatingDto.getProductName(),
                productRatingDto.getUserName(),
                productRatingDto.getShopName()
        );
        var saved = new ProductRating();
        if (rated.isPresent()) {
            if (rated.get().getRating().equals(productRatingDto.getRating())) {
                productRatingRepository.delete(rated.get());
                updateRatingProduct(beingRatedProduct);
                saved = null;
            } else {
                rated.get().setRating(productRatingDto.getRating());
                rated.get().setUpdatedTime(Date.from(Instant.now()));
                saved = productRatingRepository.save(rated.get());
                updateRatingProduct(beingRatedProduct);

            }
        } else {
            ProductRating productRating = ProductRating.builder()
                    .user(ratingUser)
                    .product(beingRatedProduct)
                    .shop(inShop)
                    .rating(productRatingDto.getRating())
                    .ratedTime(Date.from(Instant.now()))
                    .build();
            saved = productRatingRepository.save(productRating);
            updateRatingProduct(beingRatedProduct);

        }
        return modelMapper.map(saved, ProductRatingDto.class);
    }

    @Override
    public Optional<ShopRating> getRatedShop(String shopName, String userName) {
        return shopRatingRepository.findByShopNameUserName(shopName, userName);
    }

    @Override
    public ShopRatingDto rateShop(ShopRatingDto shopRatingDto) {
        User ratingUser = userRepository.findByUserName(shopRatingDto.getUserName()).get();
        Shop beingRatedShop = shopRepository.findShopByShopName(shopRatingDto.getShopName()).get();
        Optional<ShopRating> rated = shopRatingRepository.findByShopIdUserId(ratingUser.getId(),
                beingRatedShop.getId());
        ShopRating shopRating = ShopRating.builder()
                .user(ratingUser)
                .shop(beingRatedShop)
                .rating(shopRatingDto.getRating())
                .ratedTime(Date.from(Instant.now()))
                .build();
        var saved = shopRatingRepository.save(shopRating);
        updateRatingShop(shopRatingDto.getShopName());
//        beingRatedShop.s
        return modelMapper.map(saved, ShopRatingDto.class);
    }

    @Override
    @Transactional
    public void removeRateShop(ShopRatingDto shopRatingDto) {
        var ratedShop = getRatedShop(shopRatingDto.getShopName(), shopRatingDto.getUserName()).get();
        shopRatingRepository.delete(ratedShop);
        updateRatingShop(shopRatingDto.getShopName());
    }

    @Override
    public ShopRatingDto updateRateShop(ShopRatingDto shopRatingDto) {
        Optional<ShopRating> rated = getRatedShop(shopRatingDto.getShopName(), shopRatingDto.getUserName());
        rated.get().setRating(shopRatingDto.getRating());
        rated.get().setUpdatedTime(Date.from(Instant.now()));
        shopRatingRepository.save(rated.get());
        updateRatingShop(shopRatingDto.getShopName());
        return modelMapper.map(rated.get(), ShopRatingDto.class);
    }

    @Override
    public Optional<User> getUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional<ProductRating> getRatedProduct(String shopName, String userName, String productName) {
        return productRatingRepository.findByProductNameUserNameShopName(productName, shopName, userName);
    }

    @Override
    public ProductRatingDto updateRateProduct(ProductRatingDto productRatingDto) {
        Optional<ProductRating> rated = getRatedProduct(productRatingDto.getShopName(),
                productRatingDto.getUserName(),
                productRatingDto.getProductName()
        );
        rated.get().setRating(productRatingDto.getRating());
        rated.get().setUpdatedTime(Date.from(Instant.now()));
        var saved = productRatingRepository.save(rated.get());
        updateRatingProduct(productRepository.findByProductName(productRatingDto.getProductName()).get());
        return modelMapper.map(saved, ProductRatingDto.class);
    }

    @Override
    public void updateRatingProduct(Product product) {
        double fiveStarNumbers = product.getProductRatings().stream().filter((o) -> o.getRating().equals(Rating.FIVE_STAR)).count();
        double fourStarNumbers = product.getProductRatings().stream().filter((o) -> o.getRating().equals(Rating.FOUR_STAR)).count();
        double threeStarNumbers = product.getProductRatings().stream().filter((o) -> o.getRating().equals(Rating.THREE_STAR)).count();
        double twoStarNumbers = product.getProductRatings().stream().filter((o) -> o.getRating().equals(Rating.TWO_STAR)).count();
        double oneStarNumbers = product.getProductRatings().stream().filter((o) -> o.getRating().equals(Rating.ONE_STAR)).count();
        double sum = fiveStarNumbers + fourStarNumbers + threeStarNumbers + twoStarNumbers + oneStarNumbers;
        if (sum == 0) {
            sum = 1;
        }
        double finalRate = (5 * fiveStarNumbers + 4 * fourStarNumbers + 3 * threeStarNumbers + 2 * twoStarNumbers + oneStarNumbers) / sum;
        product.setRating(finalRate);
        product.setUpdatedTime(Date.from(Instant.now()));
        productRepository.save(product);
    }

    @Override
    public void updateRatingShop(String shopName) {
        var shop = shopRepository.findShopByShopName(shopName).get();
        double fiveStarNumbers = shop.getShopRatings().stream().filter((o) -> o.getRating().equals(Rating.FIVE_STAR)).count();
        double fourStarNumbers = shop.getShopRatings().stream().filter((o) -> o.getRating().equals(Rating.FOUR_STAR)).count();
        double threeStarNumbers = shop.getShopRatings().stream().filter((o) -> o.getRating().equals(Rating.THREE_STAR)).count();
        double twoStarNumbers = shop.getShopRatings().stream().filter((o) -> o.getRating().equals(Rating.TWO_STAR)).count();
        double oneStarNumbers = shop.getShopRatings().stream().filter((o) -> o.getRating().equals(Rating.ONE_STAR)).count();
        double sum = fiveStarNumbers + fourStarNumbers + threeStarNumbers + twoStarNumbers + oneStarNumbers;
        if (sum == 0) {
            sum = 1;
        }
        double finalRate = (5.0 * fiveStarNumbers + 4.0 * fourStarNumbers + 3.0 * threeStarNumbers + 2.0 * twoStarNumbers + oneStarNumbers) / (sum);
        shop.setShopRating(finalRate);
        shop.setUpdatedTime(Date.from(Instant.now()));
        shopRepository.save(shop);

    }

    @Override
    public void removeRateProduct(ProductRatingDto productRatingDto) {
        var ratedProduct = productRatingRepository.findByProductNameUserNameShopName(productRatingDto.getProductName(),
                productRatingDto.getShopName(),
                productRatingDto.getUserName());
        productRatingRepository.delete(ratedProduct.get());
        updateRatingProduct(ratedProduct.get().getProduct());
    }
}
