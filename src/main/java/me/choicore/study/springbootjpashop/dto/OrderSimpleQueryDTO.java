package me.choicore.study.springbootjpashop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.choicore.study.springbootjpashop.domain.Address;
import me.choicore.study.springbootjpashop.domain.Order;
import me.choicore.study.springbootjpashop.domain.OrderStatus;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSimpleQueryDTO {
    private Long orderId;
    private String name;
    private LocalDateTime orderDateTime;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDTO(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDateTime = order.getOrderDateTime();
        this.orderStatus = order.getOrderStatus();
        this.address = order.getDelivery().getAddress();
    }

    public static OrderSimpleQueryDTO fromEntity(Order order) {
        return OrderSimpleQueryDTO.builder()
                .orderId(order.getId())
                .name(order.getMember().getName()) // Lazy 강제 초기화
                .orderDateTime(order.getOrderDateTime())
                .orderStatus(order.getOrderStatus())
                .address(order.getDelivery().getAddress()) // Lazy 강제 초기화
                .build();
    }

}
