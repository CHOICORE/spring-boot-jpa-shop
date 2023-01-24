package me.choicore.study.springbootjpashop.web.api;


import lombok.*;
import me.choicore.study.springbootjpashop.domain.Address;
import me.choicore.study.springbootjpashop.domain.Order;
import me.choicore.study.springbootjpashop.domain.OrderStatus;
import me.choicore.study.springbootjpashop.dto.OrderSimpleQueryDTO;
import me.choicore.study.springbootjpashop.repository.OrderRepository;
import me.choicore.study.springbootjpashop.repository.OrderSearch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleAPIController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> result = orderRepository.findAllByString(new OrderSearch());

        // Lazy 강제 초기화
        for (Order order : result) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return result;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleDTO> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderSimpleDTO> result = orders.stream().map(OrderSimpleDTO::fromEntity).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleDTO> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<OrderSimpleDTO> result = orders.stream().map(OrderSimpleDTO::fromEntity).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDTO> ordersV4() {
        List<OrderSimpleQueryDTO> result = orderRepository.findOrderDTOs();
        return result;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class OrderSimpleDTO {
        private Long orderId;
        private String name;
        private LocalDateTime orderDateTime;
        private OrderStatus orderStatus;
        private Address address;

        public OrderSimpleDTO(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDateTime = order.getOrderDateTime();
            this.orderStatus = order.getOrderStatus();
            this.address = order.getDelivery().getAddress();
        }

        public static OrderSimpleDTO fromEntity(Order order) {
            return OrderSimpleDTO.builder()
                    .orderId(order.getId())
                    .name(order.getMember().getName()) // Lazy 강제 초기화
                    .orderDateTime(order.getOrderDateTime())
                    .orderStatus(order.getOrderStatus())
                    .address(order.getDelivery().getAddress()) // Lazy 강제 초기화
                    .build();
        }

    }
}
