package me.choicore.study.springbootjpashop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.choicore.study.springbootjpashop.domain.Address;
import me.choicore.study.springbootjpashop.domain.Order;
import me.choicore.study.springbootjpashop.domain.OrderItem;
import me.choicore.study.springbootjpashop.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;
    private String name;
    private LocalDateTime orderDateTime;
    private OrderStatus orderStatus;
    private Address address;

    @Builder.Default
    private List<OrderItemDTO> orderItems = new ArrayList<>();

    public OrderDTO(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDateTime = order.getOrderDateTime();
        this.orderStatus = order.getOrderStatus();
        this.address = order.getDelivery().getAddress();
        this.orderItems = toOrderItemDTO(order);

    }

    public static OrderDTO fromEntity(Order order) {

        return OrderDTO.builder()
                .orderId(order.getId())
                .name(order.getMember().getName())
                .orderDateTime(order.getOrderDateTime())
                .orderStatus(order.getOrderStatus())
                .address(order.getDelivery().getAddress())
                .orderItems(toOrderItemDTO(order))
                .build();
    }

    private static List<OrderItemDTO> toOrderItemDTO(Order order) {
        return order.getOrderItems().stream().map(OrderItemDTO::fromEntity).collect(Collectors.toList());
    }
}
