package com.intern.project1.services;

import com.intern.project1.entities.dto.CartDto;
import com.intern.project1.entities.dto.OrderDetailDto;
import com.intern.project1.entities.dto.OrderDto;
import com.intern.project1.entities.dto.PeriodTime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderServices {
    List<OrderDetailDto> getOrdersPagedByShopId(Long shopId, Integer pageIndex);

    //Lay danh sach don hang cua shop theo khoang thoi gian
    List<OrderDetailDto> getOrdersInPeriodTime(String shopName, PeriodTime periodTime);

    List<OrderDetailDto> getOrdersByShopNameInMonthOfYear(String shopName, PeriodTime periodTime);

    boolean isProductQuantityEnough(CartDto cartDto);

    List<OrderDto> checkOut(CartDto cartDto);

    //Lay danh sach order cua khach hang theo khoang thoi gian
    List<OrderDto> getOrdersByUserNameAndPeriod(String userName, Integer beginYear, Integer beginMonth,
                                                Integer endYear, Integer endMonth);

    List<OrderDetailDto> getCustomerOrder(String userName, int pageSelection);

    OrderDetailDto manageOrder(OrderDetailDto orderDetailDto);
}
