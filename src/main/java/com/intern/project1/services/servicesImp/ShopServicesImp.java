package com.intern.project1.services.servicesImp;

import com.intern.project1.entities.*;
import com.intern.project1.entities.dto.*;
import com.intern.project1.constant.enums.Category;
import com.intern.project1.constant.enums.OrderStatus;
import com.intern.project1.constant.enums.ShopStatus;
import com.intern.project1.exceptions.ResourceAlreadyExistedException;
import com.intern.project1.exceptions.WrongInformationException;
import com.intern.project1.repositories.*;
import com.intern.project1.services.ProductServices;
import com.intern.project1.services.ShopServices;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopServicesImp implements ShopServices {
    private final ShopRepository shopRepository;
    private final Shop_ProductRepository shopProductRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final Order_ProductsRepository order_ProductsRepository;
    private final ProductServices productServices;
    @Value("${newShopProfitFee}")
    private double newShopProfitFee;
    @Value("${profitFee}")
    private double profitFee;

    @Override
    public List<Shop_ProductDto> getShopProductsByPageIndexAndSortedBySold(String shopName, Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10, Sort.by("sold").descending());
        return shopProductRepository.findByShopNameOrderBySoldDesc(shopName, pageable)
                .stream().map((element) -> modelMapper.map(element, Shop_ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getShopProductsPageIndexSortedByCreatedTimeDesc(String shopName, Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        return shopProductRepository.findByShopNameOrderBySoldDesc(shopName, pageable)
                .stream().map((element) -> modelMapper.map(element, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShopDto> getAllShopSortedByRatingDesc(Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        return shopRepository.findAllShopOrderByRatingDesc(pageable)
                .stream().map((element) -> modelMapper.map(element, ShopDto.class))
                .collect(Collectors
                        .toList());
    }

    @Override
    public List<ShopDto> getShopsByShopCategorySortedByRatingDesc(Category category, Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        return shopRepository.findShopsByCategorySortedByShopRatingDesc(category, pageable)
                .stream().map((element) -> modelMapper.map(element, ShopDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShopDto> getShopsByAddressSortedByRatingDesc(String shopAddress, Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        return shopRepository.findShopsByAddressSortedByShopRatingDesc(shopAddress, pageable)
                .stream().map((element) -> modelMapper.map(element, ShopDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShopDto> getShopsByShopNameSortedByRatingDesc(String shopName, Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        return shopRepository.findShopsByShopNameOrderByShopRatingDesc(shopName, pageable)
                .stream().map((element) -> modelMapper.map(element, ShopDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Shop_ProductDto createShopProductObject(String shopName, ProductDto productDto, Integer productQuantity) throws ResourceAlreadyExistedException {
        Shop_Product newProductToShop = Shop_Product.builder()
                .shop(shopRepository.findShopByShopName(shopName).get())
                .product(productRepository.findByProductName(productDto.getProductName()).get())
                .quantity(productQuantity)
                .createdTime(Date.from(Instant.now()))
                .build();
        shopProductRepository.save(newProductToShop);
        return modelMapper.map(newProductToShop, Shop_ProductDto.class);
    }

    @Override
    public boolean isShopHasThisProduct(Shop_ProductDto shopProductDto) {
        return shopProductRepository.findByProductNameAndShopName(shopProductDto.getProductName(), shopProductDto.getShopName()).isPresent();
    }

    @Override
    public Shop_ProductDto addProductToShop(Shop_ProductDto shopProductDto) {
        var savedShopProduct = new Shop_Product();
        Optional<Shop_Product> getShopProductInfo = shopProductRepository.findByProductNameAndShopName(shopProductDto.getProductName(), shopProductDto.getShopName());
        if (productServices.isProductExisted(shopProductDto.getProductName())
                && getShopProductInfo.isEmpty()) {
            Shop_Product newShopProduct = Shop_Product.builder()
                    .shop(shopRepository.findShopByShopName(shopProductDto.getShopName()).get())
                    .product(productRepository.findByProductName(shopProductDto.getProductName()).get())
                    .quantity(shopProductDto.getQuantity())
                    .sold(0)
                    .createdTime(Date.from(Instant.now()))
                    .build();
            savedShopProduct = shopProductRepository.save(newShopProduct);
        } else if (getShopProductInfo.isPresent()) {
            getShopProductInfo.get().setQuantity(shopProductDto.getQuantity());
            savedShopProduct = shopProductRepository.save(getShopProductInfo.get());
        }
        return modelMapper.map(savedShopProduct, Shop_ProductDto.class);
    }


    @Override
    public OrderDto manageOrder(Long orderId, OrderStatus orderStatus) {
        Order orderBeingManaged = orderRepository.findById(orderId).get();
        if (!orderBeingManaged.getOrderStatus().equals(orderStatus)) {
            orderBeingManaged.setOrderStatus(orderStatus);
            orderBeingManaged.setUpdateTime(Date.from(Instant.now()));
            orderRepository.save(orderBeingManaged);
        }
        return modelMapper.map(orderBeingManaged, OrderDto.class);

    }

    @Override
    public Optional<Shop_Product> getShopProduct(String shopName, String productName) {
        return shopProductRepository.findByProductNameAndShopName(shopName, productName);
    }

    @Override
    public Shop_ProductDto changeProductQuantity(Shop_ProductDto shopProductDto) {
        var editingShopProduct = shopProductRepository.findByProductNameAndShopName(shopProductDto.getShopName(), shopProductDto.getProductName());
        editingShopProduct.get().setQuantity(shopProductDto.getQuantity());
        var changed = shopProductRepository.save(editingShopProduct.get());
        return modelMapper.map(changed, Shop_ProductDto.class);
    }

    @Override
    public Shop_ProductDto deceaseProduct(@Positive Integer productQuantityDecreasing, Shop_ProductDto currentShopProductDto) {
        Shop_Product currentShopProduct = shopProductRepository.findByProductNameAndShopName(currentShopProductDto.getProductName(), currentShopProductDto.getShopName()).get();
        if (productQuantityDecreasing > currentShopProduct.getQuantity()) {
            throw new WrongInformationException("Decrease quantity can not over your shop own");
        } else {
            currentShopProduct.setQuantity(currentShopProduct.getQuantity() - productQuantityDecreasing);
            currentShopProduct.setUpdatedTime(Date.from(Instant.now()));
            shopProductRepository.save(currentShopProduct);
        }
        return modelMapper.map(currentShopProduct, Shop_ProductDto.class);
    }

    @Override
    public Double getShopIncome(Long shopId) {
        List<Long> listOrder = orderRepository.findOrderIdsByShopId(shopId);
        Double shopIncome = 0.0;
        for (Long orderId : listOrder) {
            shopIncome += order_ProductsRepository.findOrderProductsByOrderId(orderId).stream()
                    .map(Order_Products::getPrice)
                    .mapToDouble(Double::doubleValue).sum();
        }
        return shopIncome;
    }

    @Override
    public Double getShopIncomeByMonth(Long shopId, Integer month, Integer year) {
        Calendar begindCalendar = Calendar.getInstance();
        begindCalendar.set(year, month - 1, 1);
        Date beginDate = begindCalendar.getTime();
        Calendar endCalendar = Calendar.getInstance();
        List<Integer> monthsHave30days = List.of(4, 6, 9, 11);
        if (month == 2) {
            if (year % 4 == 0) {
                if (year % 100 == 0) {
                    if (year % 400 == 0) {
                        endCalendar.set(year, month - 1, 29);
                    } else {
                        endCalendar.set(year, month - 1, 28);
                    }
                } else {
                    endCalendar.set(year, month - 1, 29);
                }
            } else {
                endCalendar.set(year, month - 1, 28);
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            endCalendar.set(year, year - 1, 30);
        } else {
            endCalendar.set(year, month - 1, 31);
        }
        Date endDate = endCalendar.getTime();
        Pageable pageable = PageRequest.of(0, 10);
        List<Long> listOrderInMonth = orderRepository.findOrderIdsByShopIdAndMonthYear(shopId, beginDate, endDate, pageable);
        Double shopIncomeInMonth = 0.0;
        for (Long orderId : listOrderInMonth) {
            shopIncomeInMonth += order_ProductsRepository.findOrderProductsByOrderIdAndPeriod(orderId, beginDate, endDate).stream()
                    .map(Order_Products::getPrice)
                    .mapToDouble(Double::doubleValue).sum();
        }
        return shopIncomeInMonth;
    }

    @Override
    public int getAllShopNumbers() {
        return shopRepository.findAll().size();
    }

    @Override
    public int getShopNumbersOfCategory(Category category) {
        return shopRepository.findShopsByCategoty(category).size();
    }

    @Override
    public int getShopNumbersOfShopName(String shopName) {
        return shopRepository.findShopsByShopName(shopName).size();
    }

    @Override
    public List<ShopDto> getShopsByShopAddressSortedByRatingDesc(String shopAddress, int pageSelection) {
        Pageable pageable = PageRequest.of(pageSelection - 1, 10);
        return shopRepository.findShopsByAddressSortedByShopRatingDesc(shopAddress, pageable).stream().map((element) -> modelMapper.map(element, ShopDto.class)).toList();
    }

    @Override
    public int getShopNumbersOfShopAddress(String shopAddress) {
        return shopRepository.findShopsByShopAddress(shopAddress).size();
    }


    @Override
    public Optional<Shop> findShopByUserName(String userName) {
        return shopRepository.findByUserName(userName);
    }

    @Override
    public ShopDto shopRegister(ShopDto shopDto) {
        Shop newShop = modelMapper.map(shopDto, Shop.class);
        newShop.setUser(userRepository.findByUserName(shopDto.getUserName()).get());
        newShop.setApproved(false);
        newShop.setShopStatus(ShopStatus.DEACTIVATED);
        newShop.setCreatedTime(Date.from(Instant.now()));
        var savedShop = shopRepository.save(newShop);
        return modelMapper.map(savedShop, ShopDto.class);
    }

    @Override
    public ShopInfo updateShopInformation(ShopInfo shopInfo) {
        Shop currentShop = shopRepository.findShopByShopName(shopInfo.getUserName()).get();
        if (shopRepository.findShopByShopName(shopInfo.getShopName()).isPresent()
                && !currentShop.getShopName().equals(shopInfo.getShopName())) {
            throw new ResourceAlreadyExistedException("This shop's name has already existed, please use another");
        } else if (!currentShop.getShopName().equals(shopInfo.getShopName())) {
            currentShop.setShopName(shopInfo.getShopName());
        }
        if (!currentShop.getShopCategory().equals(shopInfo.getShopCategory())) {
            currentShop.setShopCategory(shopInfo.getShopCategory());
        }
        if (!currentShop.getAddress().equals(shopInfo.getAddress())) {
            currentShop.setAddress(shopInfo.getAddress());
        }
        if (!currentShop.getShopStatus().equals(shopInfo.getShopStatus())) {
            currentShop.setShopStatus(shopInfo.getShopStatus());
        }
        currentShop.setUpdatedTime(Date.from(Instant.now()));
        var savedShopInfo = shopRepository.save(currentShop);
        return modelMapper.map(savedShopInfo, ShopInfo.class);
    }


    @Override
    public ShopDto getShopByUserName(String userName) {
        var shop = shopRepository.findByUserName(userName).get();
        return modelMapper.map(shop, ShopDto.class);
    }

    @Override
    public Double getShopIncomeByMonthYear(String shopName, PeriodTime monthYear) {
        var shop = shopRepository.findShopByShopName(shopName);
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        begin.set(monthYear.getBeginYear(), monthYear.getBeginMonth() - 1, 1);
        Date beginTime = begin.getTime();
        end.set(monthYear.getBeginYear(), monthYear.getBeginMonth() - 1, 31);
        Date endTime = end.getTime();
        return orderRepository.findOrdersByShopIdAndPeriod(shop.get().getId(), beginTime, endTime)
                .stream().mapToDouble(Order::getPrice).sum();
    }

    @Override
    public Double getShopProfitByMonthYear(String shopName, PeriodTime monthYear) {
        var shop = shopRepository.findShopByShopName(shopName);
        var shopIncome = getShopIncomeByMonthYear(shopName, monthYear);
        return shopIncome - (shopIncome * shop.get().getProfitFee());
    }

    @Override
    public List<ShopManagement> getAllShop(int pageSelected) {
        Pageable pageable = PageRequest.of(pageSelected - 1, 10);
        return shopRepository.findAll(pageable).stream().map(this::mapShopToManagement).toList();
    }

    private ShopManagement mapShopToManagement(Shop shop) {
        return ShopManagement.builder()
                .shopProductName(shop.getShopProducts().stream().map((o) -> o.getProduct().getProductName()).toList())
                .shopRatingIds(shop.getShopRatings().stream().map(ShopRating::getId).toList())
                .approved(shop.getApproved())
                .shopStatus(shop.getShopStatus())
                .profitFee(shop.getProfitFee())
                .userName(shop.getUser().getUserName())
                .shopName(shop.getShopName())
                .address(shop.getAddress())
                .shopCategory(shop.getShopCategory())
                .createdTime(shop.getCreatedTime())
                .updatedTime(shop.getUpdatedTime())
                .build();
    }

    @Override
    public ShopManagement approveShopRequest(ShopManagement shopManagement) {
        var shop = shopRepository.findShopByShopNameAdmin(shopManagement.getShopName()).get();
        shop.setShopStatus(shopManagement.getShopStatus());
        shop.setApproved(shopManagement.getApproved());
        shop.setProfitFee(newShopProfitFee);
        shop.setCreatedTime(Date.from(Instant.now()));
        var approvedShop = shopRepository.save(shop);
        return mapShopToManagement(approvedShop);
    }

    @Override
    public void setProfitFee() {
        Long today = Date.from(Instant.now()).getTime();
        List<Shop> allShop = shopRepository.findAll();
        for (Shop shop : allShop) {
            if (today - shop.getCreatedTime().getTime() >= 2678400000L) {
                shop.setProfitFee(profitFee);
            }
        }
    }

    @Override
    public ShopManagement getShopByShopNameForAdmin(String shopName) {
        return mapShopToManagement(shopRepository.findShopByShopName(shopName).get());
    }

    @Override
    public ShopManagement changeShopStatus(ShopManagement shopManagement) {
        var shop = shopRepository.findShopByShopName(shopManagement.getShopName()).get();
        shop.setShopStatus(shopManagement.getShopStatus());
        shop.setApproved(shopManagement.getApproved());
        var changedStatus = shopRepository.save(shop);
        return mapShopToManagement(changedStatus);
    }

    @Override
    public ShopShowDto getShopByShopName(String shopName) {
        var found = shopRepository.findShopByShopName(shopName).get();
        return ShopShowDto.builder()
                .shopRating(found.getShopRating())
                .shopName(found.getShopName())
                .shopStatus(found.getShopStatus())
                .address(found.getAddress())
                .shopCategory(found.getShopCategory())
                .address(found.getAddress())
                .createdTime(found.getCreatedTime())
                .updatedTime(found.getUpdatedTime())
                .build();
    }

    @Override
    public Optional<ShopDto> findShopByUserId(Long userId) {
        return shopRepository.findShopByUserId(userId);
    }

    @Override
    public List<ShopManagement> getNotApprovedShop() {
        return shopRepository.findNotApprovedShop().stream().map(this::mapShopToManagement).toList();
    }

}
