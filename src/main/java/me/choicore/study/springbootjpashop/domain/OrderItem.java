package me.choicore.study.springbootjpashop.domain;


import lombok.*;
import me.choicore.study.springbootjpashop.domain.item.Item;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    private int orderPrice;
    private int count;


    /* 생성 메서드 */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.decrementStock(count);
        return orderItem;
    }


    /* 비즈니스 로직 */
    public void cancel() {
        this.item.incrementStock(this.count);
    }

    /**
     * 주문 상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return this.getOrderPrice() * this.getCount();
    }
}