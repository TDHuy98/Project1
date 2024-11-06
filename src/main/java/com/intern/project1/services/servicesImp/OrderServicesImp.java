package com.intern.project1.services.servicesImp;

import com.intern.project1.entities.*;
import com.intern.project1.entities.dto.*;
import com.intern.project1.constant.enums.OrderStatus;
import com.intern.project1.repositories.*;
import com.intern.project1.services.OrderServices;
import com.intern.project1.services.authentication.AuthenticationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class OrderServicesImp implements OrderServices {
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private final CartRepository cartRepository;
    private final Cart_Products_ShopsRepository cart_Products_ShopsRepository;
    private final Order_ProductsRepository order_ProductsRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final AuthenticationService authService;
    private final Shop_ProductRepository shop_ProductRepository;

    //Lay tat ca orders cua shop theo Id
    @Override
    public List<OrderDetailDto> getOrdersPagedByShopId(Long shopId, Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        return orderRepository.findOrdersByShopIdSortedByCreatedTime(shopId, pageable)
                .stream().map(this::makeOrderDetail).toList();
    }

    //Lay danh sach don hang cua shop theo khoang thoi gian
    @Override
    public List<OrderDetailDto> getOrdersInPeriodTime(String shopName, PeriodTime periodTime) {
        var shop = shopRepository.findShopByShopName(shopName);
        Calendar begindCalendar = Calendar.getInstance();
        begindCalendar.set(periodTime.getBeginYear(), periodTime.getBeginMonth() - 1, 1);
        Date beginDate = begindCalendar.getTime();
        Calendar endCalendar = Calendar.getInstance();
        List<Integer> monthsHave30days = List.of(4, 6, 9, 11);
        if (periodTime.getEndMonth() == 2) {
            if (periodTime.getEndYear() % 4 == 0) {
                if (periodTime.getEndYear() % 100 == 0) {
                    if (periodTime.getEndYear() % 400 == 0) {
                        endCalendar.set(periodTime.getEndYear(), periodTime.getEndMonth() - 1, 29);
                    } else {
                        endCalendar.set(periodTime.getEndYear(), periodTime.getEndMonth() - 1, 28);
                    }
                } else {
                    endCalendar.set(periodTime.getEndYear(), periodTime.getEndMonth() - 1, 29);
                }
            } else {
                endCalendar.set(periodTime.getEndYear(), periodTime.getEndMonth() - 1, 28);
            }
        } else if (periodTime.getEndMonth() == 4 || periodTime.getEndMonth() == 6 || periodTime.getEndMonth() == 9 || periodTime.getEndMonth() == 11) {
            endCalendar.set(periodTime.getEndYear(), periodTime.getEndMonth() - 1, 30);
        } else {
            endCalendar.set(periodTime.getEndYear(), periodTime.getEndMonth() - 1, 31);
        }
        Date endDate = endCalendar.getTime();
        Pageable pageable = PageRequest.of(0, 10);
        return orderRepository.findOrdersByShopIdAndPeriod(shop.get().getId(), beginDate, endDate, pageable)
                .stream().map(this::makeOrderDetail).toList();
    }

    @Override
    public List<OrderDetailDto> getOrdersByShopNameInMonthOfYear(String shopName, PeriodTime periodTime) {
        var shop = shopRepository.findShopByShopName(shopName);
        Calendar begindCalendar = Calendar.getInstance();
        begindCalendar.set(periodTime.getBeginYear(), periodTime.getBeginMonth() - 1, 1);
        Date beginDate = begindCalendar.getTime();
        Calendar endCalendar = Calendar.getInstance();
        List<Integer> monthsHave30days = List.of(4, 6, 9, 11);
        if (periodTime.getEndMonth() == 2) {
            if (periodTime.getBeginYear() % 4 == 0) {
                if (periodTime.getBeginYear() % 100 == 0) {
                    if (periodTime.getBeginYear() % 400 == 0) {
                        endCalendar.set(periodTime.getBeginYear(), periodTime.getBeginMonth() - 1, 29);
                    } else {
                        endCalendar.set(periodTime.getBeginYear(), periodTime.getBeginMonth() - 1, 28);
                    }
                } else {
                    endCalendar.set(periodTime.getEndYear(), periodTime.getBeginMonth() - 1, 29);
                }
            } else {
                endCalendar.set(periodTime.getBeginYear(), periodTime.getBeginMonth() - 1, 28);
            }
        } else if (periodTime.getBeginMonth() == 4 || periodTime.getBeginMonth() == 6 || periodTime.getBeginMonth() == 9 || periodTime.getBeginMonth() == 11) {
            endCalendar.set(periodTime.getBeginYear(), periodTime.getBeginMonth() - 1, 30);
        } else {
            endCalendar.set(periodTime.getBeginYear(), periodTime.getBeginMonth() - 1, 31);
        }
        Date endDate = endCalendar.getTime();
        Pageable pageable = PageRequest.of(0, 10);
        return orderRepository.findOrdersByShopIdAndPeriod(shop.get().getId(), beginDate, endDate, pageable)
                .stream().map(this::makeOrderDetail).toList();
    }

    private Order_Products cartProductsShopsToOrderProducts(Cart_Products_Shops cartProductsShops, Long orderId) {
        Order_Products mapToOrderProductsDto = Order_Products.builder()
                .order(orderRepository.findById(orderId).get())
                .product(productRepository.findById(cartProductsShops.getProduct().getId()).get())
                .quantity(cartProductsShops.getQuantity())
                .price(productRepository.findById(cartProductsShops.getProduct().getId()).get().getProductPrice() * cartProductsShops.getQuantity())
                .build();
        return mapToOrderProductsDto;
    }


    private Order orderDtoToOrder(OrderDto orderDto) {
        Order convertedToOrder = Order.builder()
                .users(userRepository.findById(orderDto.getUsersId()).get())
//                .orderProducts(orderDto.getOrderProductIds().stream().map((o) -> modelMapper.map(o, Order_Products.class)).toList())
                .shop(shopRepository.findById(orderDto.getShopId()).get())
                .orderStatus(orderDto.getOrderStatus())
                .build();
        return convertedToOrder;
    }

    @Override
    public boolean isProductQuantityEnough(CartDto cartDto) {
        Cart currentCart = cartRepository.findByUserName(cartDto.getUserName()).get();
        List<Shop> shops = currentCart.getCartProductsShops().stream().map(Cart_Products_Shops::getShop).distinct().toList();
        List<Cart_Products_Shops> cpsList = currentCart.getCartProductsShops();
        for (Shop shop : shops) {
            List<Cart_Products_Shops> cartProductsShops = cart_Products_ShopsRepository
                    .findCart_Products_ShopsByCartIdAndShopId(currentCart.getId(), shop.getId());
            for (Cart_Products_Shops cps : cartProductsShops) {
                int productQuantity = shop_ProductRepository.findByProductNameAndShopName(
                        cps.getProduct().getProductName(),
                        cps.getShop().getShopName()
                ).get().getQuantity();
                return cps.getQuantity() <= productQuantity;
            }
        }
        return true;
    }

    //Service thanh toan cho khach hang
    @Override
    public List<OrderDto> checkOut(CartDto cartDto) {
        List<OrderDto> newOrderDtos = new ArrayList<>();

        Optional<User> currentUser = userRepository.findByUserName(cartDto.getUserName());
        //lay cart cua nguoi dung hien tai
        Cart currentCart = cartRepository.findByUserName(cartDto.getUserName()).get();
        //lay danh sach cac shop trong cart
        List<Shop> shops = cart_Products_ShopsRepository.findCartProductsShopsByCartId(currentCart.getId())
                .stream()
                .map(Cart_Products_Shops::getShop)
                .distinct().toList();

        //tao order tuong ung voi cac shop
        for (Shop shop : shops) {
            //lay chi tiet product cua shop trong cart
            List<Cart_Products_Shops> cartProductsShopsByCartIdAndShopId
                    = cart_Products_ShopsRepository.findCart_Products_ShopsByCartIdAndShopId(currentCart.getId(), shop.getId());
            //tao mot order voi cac thong tin tuong ung cua userId (cart tuong ung), shopName
            Order newOrder = Order.builder()
                    .users(currentUser.get())
                    .shop(shop)
                    .orderStatus(OrderStatus.PENDING)
                    .createdTime(Date.from(Instant.now()))
                    .build();
            //luu order vua tao lai
            var savedNewOrder = orderRepository.save(newOrder);
            //voi moi chi tiet product cua shop trong cart thi chuyen thanh chi tiet product cua shop trong order
            for (Cart_Products_Shops cps : cartProductsShopsByCartIdAndShopId) {
                Order_Products newOrder_Products = cartProductsShopsToOrderProducts(cps, savedNewOrder.getId());
                newOrder_Products.setCreatedTime(Date.from(Instant.now()));
                var savedOrderProduct = order_ProductsRepository.save(newOrder_Products);
                var shopProductInfo = shop_ProductRepository.findByProductNameAndShopName(savedOrderProduct.getProduct().getProductName(),
                        savedOrderProduct.getOrder().getShop().getShopName()).get();
                shopProductInfo.setQuantity(shopProductInfo.getQuantity() - savedOrderProduct.getQuantity());
                shopProductInfo.setSold(shopProductInfo.getSold() + savedOrderProduct.getQuantity());
                shop_ProductRepository.save(shopProductInfo);
                var soldProduct = productRepository.findByProductName(savedOrderProduct.getProduct().getProductName()).get();
                soldProduct.setSold(soldProduct.getSold() + savedOrderProduct.getQuantity());
                productRepository.save(soldProduct);
            }
            newOrder.setPrice(order_ProductsRepository.findOrderProductsByOrderId(newOrder.getId())
                    .stream().mapToDouble(Order_Products::getPrice).sum());
            orderRepository.save(newOrder);
            newOrderDtos.add(modelMapper.map(newOrder, OrderDto.class));
        }
        //xoa danh sach san pham trong gio hang cua nguoi dung sau khi thanh toan xong
        cart_Products_ShopsRepository.deleteByCartId(currentCart.getId());
        currentCart.setPrice(0.0);
        currentCart.setUpdatedTime(Date.from(Instant.now()));
        cartRepository.save(currentCart);
        return newOrderDtos;
    }


    //Lay danh sach order cua khach hang theo khoang thoi gian
    @Override
    public List<OrderDto> getOrdersByUserNameAndPeriod(String userName, Integer beginYear, Integer beginMonth,
                                                       Integer endYear, Integer endMonth) {
        Calendar begindCalendar = Calendar.getInstance();
        begindCalendar.set(beginYear, beginMonth - 1, 1);
        Date beginDate = begindCalendar.getTime();
        Calendar endCalendar = Calendar.getInstance();
        List<Integer> monthsHave30days = List.of(4, 6, 9, 11);
        if (endMonth == 2) {
            if (endYear % 4 == 0) {
                if (endYear % 100 == 0) {
                    if (endYear % 400 == 0) {
                        endCalendar.set(endYear, endMonth - 1, 29);
                    } else {
                        endCalendar.set(endYear, endMonth - 1, 28);
                    }
                } else {
                    endCalendar.set(endYear, endMonth - 1, 29);
                }
            } else {
                endCalendar.set(endYear, endMonth - 1, 28);
            }
        } else if (endMonth == 4 || endMonth == 6 || endMonth == 9 || endMonth == 11) {
            endCalendar.set(endYear, endMonth - 1, 30);
        } else {
            endCalendar.set(endYear, endMonth - 1, 31);
        }
        Date endDate = endCalendar.getTime();
        Pageable pageable = PageRequest.of(0, 10);

        return orderRepository.findOrdersByUserNameAndPeriod(userRepository.findByUserName(userName).get().getId(), beginDate, endDate, pageable)
                .stream().map((element) -> modelMapper.map(element, OrderDto.class))
                .collect(Collectors
                        .toList());
    }

    @Override
    public List<OrderDetailDto> getCustomerOrder(String userName, int pageSelection) {
        //lay thong tin cua customer hien tai
        User currentCustomer = userRepository.findByUserName(userName).get();
        //lay tat ca order cua customer
        //voi moi order cua customer thi tong hop chi tiet product cua order vao mot orderDetailDto
        return orderRepository.findOrdersByUserId(currentCustomer.getId()).stream().map(this::makeOrderDetail).toList();
    }

    private OrderProductsDetail orderProductsToDto(Order_Products orderProducts) {
        return OrderProductsDetail.builder()
                .productProductName(orderProducts.getProduct().getProductName())
                .quantity(orderProducts.getQuantity())
                .price(orderProducts.getPrice())
                .build();
    }

    private OrderDetailDto makeOrderDetail(Order order) {
        List<OrderProductsDetail> orderDetail = order_ProductsRepository.findOrderProductsByOrderId(order.getId())
                .stream()
                .map((this::orderProductsToDto))
                .toList();
        return OrderDetailDto.builder()
                .orderId(order.getId())
                .userName(order.getUsers().getUserName())
                .shopName(order.getShop().getShopName())
                .orderProductsDetail(orderDetail)
                .price(orderDetail.stream().mapToDouble(OrderProductsDetail::getPrice).sum())
                .orderStatus(order.getOrderStatus())
                .createdTime(order.getCreatedTime())
                .updateTime(order.getUpdateTime())
                .build();
    }

    @Override
    public OrderDetailDto manageOrder(OrderDetailDto orderDetailDto) {
        var order = orderRepository.findById(orderDetailDto.getOrderId()).get();
        order.setOrderStatus(orderDetailDto.getOrderStatus());
        var managed = orderRepository.save(order);

        return makeOrderDetail(managed);
    }
}
