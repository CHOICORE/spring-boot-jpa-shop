package me.choicore.study.springbootjpashop.web.api;


import lombok.RequiredArgsConstructor;
import me.choicore.study.springbootjpashop.domain.Order;
import me.choicore.study.springbootjpashop.domain.OrderItem;
import me.choicore.study.springbootjpashop.dto.OrderDTO;
import me.choicore.study.springbootjpashop.repository.order.OrderRepository;
import me.choicore.study.springbootjpashop.repository.order.OrderSearch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderAPIController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> result = orderRepository.findAllByString(new OrderSearch());

        // Lazy 강제 초기화
        for (Order order : result) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            for (OrderItem orderItem : order.getOrderItems()) {
                orderItem.getItem().getName();
            }
        }
        return result;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDTO> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDTO> result = orders.stream().map(OrderDTO::fromEntity).collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDTO> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        for (Order order : orders) {
            System.out.println("order ref = " + order + " id = " + order.getId());
        }
        List<OrderDTO> result = orders.stream().map(OrderDTO::fromEntity).collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDTO> ordersV3_1_Page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                          @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,limit);
        for (Order order : orders) {
            System.out.println("order ref = " + order + " id = " + order.getId());
        }
        List<OrderDTO> result = orders.stream().map(OrderDTO::fromEntity).collect(Collectors.toList());

        return result;
    }
}
