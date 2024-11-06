package com.intern.project1.services.servicesImp;

import com.intern.project1.configurations.securityConfiguration.JwtService;
import com.intern.project1.entities.*;
import com.intern.project1.entities.dto.*;
import com.intern.project1.exceptions.WrongInformationException;
import com.intern.project1.repositories.*;
import com.intern.project1.services.CartServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class CartServicesImp implements CartServices {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Cart_Products_ShopsRepository cartProductsShopsRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private Shop_ProductRepository shop_ProductRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @Override
    public Cart initCart(Long userId) {
        Cart initCart = Cart.builder()
                .user(userRepository.findById(userId).get())
                .price(0.0)
                .createdTime(Date.from(Instant.now()))
                .build();
        return cartRepository.save(initCart);
    }

    @Override
    public Optional<CartDto> getCart(String userName) {
        return cartRepository.findByUserName(userName).map(this::mapCartToDto);
    }


    @Override
    public CartDto addProductToCart(ProductToCart product) {
        //lay Cart Object cua user hien tai
        Cart currentCart = cartRepository.findByUserName(product.getUserName()).get();
        Product getProduct = productRepository.findByProductName(product.getProductName()).get();
        Shop currentShop = shopRepository.findByShopName(product.getShopName()).get();
        //lay cart id
        Long currentCartId = cartRepository.findByUserName(product.getUserName()).get().getId();
        if (shop_ProductRepository.findByProductNameAndShopName(
                        product.getProductName(),
                        product.getShopName())
                .get().getQuantity() >= product.getProductQuantity()) {
            //kiem tra xem product nay da co trong gio hang chua neu co thi tang so luong len
            if (cartProductsShopsRepository.findByProductIdShopIdCardId(currentShop.getId(), getProduct.getId(), currentCart.getId()
            ).isPresent()
            ) {
                Cart_Products_Shops cps = cartProductsShopsRepository.findByProductIdShopIdCardId(currentShop.getId(), getProduct.getId(), currentCart.getId()
                        )
                        .get();
                cps.setQuantity(cps.getQuantity() + product.getProductQuantity());
                cartProductsShopsRepository.save(cps);
            } else {
                //them product vao cart tham chieu qua bang trung gian
                Cart_Products_Shops addProductToCart = Cart_Products_Shops.builder()
                        .cart(currentCart)
                        .product(getProduct)
                        .quantity(product.getProductQuantity())
                        .shop(currentShop)
                        //tinh tong gia tri cua product nay trong cart
                        .price(getProduct.getProductPrice() * product.getProductQuantity())
                        .addedTime(Date.from(Instant.now()))
                        .build();
                //luu lai object Cart_Products_Shops vao database
                cartProductsShopsRepository.save(addProductToCart);
            }
            //Tinh tong gia tri cua cac product trong cart
            Double cartPrice = cartProductsShopsRepository.findCartProductsShopsByCartId(currentCartId)
                    .stream().mapToDouble(Cart_Products_Shops::getPrice).sum();
            //Cap nhat gia tri cua Cart
            currentCart.setPrice(cartPrice);
            //Cap nhat danh sach product ID cua Cart
            currentCart.setCartProductsShops(cartProductsShopsRepository.findCartProductsShopsByCartId(currentCartId));
            //Cap nhat thoi gian thay doi
            currentCart.setUpdatedTime(Date.from(Instant.now()));
            //Luu lai Cart
            currentCart = cartRepository.save(currentCart);
        } else throw new WrongInformationException("You can not buy more than the quantity of this product in shop");
        //Tra ve CartDto cua cart hien tai
        return mapCartToDto(currentCart);
    }

    private CartDto mapCartToDto(Cart cart) {
        return CartDto.builder()
                .price(cart.getPrice())
                .userName(cart.getUser().getUserName())
                .cartProductsShopsDtos(cart.getCartProductsShops().stream().map(this::mapCart_Product_ShopToDto).toList())
                .createdTime(cart.getCreatedTime())
                .updatedTime(cart.getUpdatedTime())
                .build();
    }

    private Cart_Products_ShopsDto mapCart_Product_ShopToDto(Cart_Products_Shops cps) {
        return Cart_Products_ShopsDto.builder()
                .cartId(cps.getCart().getId())
                .shopName(cps.getShop().getShopName())
                .productName(cps.getProduct().getProductName())
                .price(cps.getPrice())
                .quantity(cps.getQuantity())
                .addedTime(cps.getAddedTime())
                .updatedTime(cps.getUpdatedTime())
                .build();
    }

    @Override
    public Cart_Products_ShopsDto deleteProductFromCart(Cart_Products_ShopsDto cartProductsShopsDto) {
        var found = cartProductsShopsRepository.findByCartIdShopNameProductName(
                cartProductsShopsDto.getCartId(), cartProductsShopsDto.getShopName(),
                cartProductsShopsDto.getProductName()
        );
        cartProductsShopsRepository.delete(found.get());
        return mapCart_Product_ShopToDto(found.get());
    }

    @Override
    public CartDto changeQuantity(Cart_Products_ShopsDto cps) {
        var editing = cartProductsShopsRepository.findByCartIdShopNameProductName(cps.getCartId(),
                cps.getShopName(),
                cps.getProductName()
        );
        editing.get().setQuantity(cps.getQuantity());
        cartProductsShopsRepository.save(editing.get());
        return mapCartToDto(cartRepository.findById(cps.getCartId()).get());
    }
}
