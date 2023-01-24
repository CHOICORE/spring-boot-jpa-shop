package me.choicore.study.springbootjpashop.service;


import lombok.RequiredArgsConstructor;
import me.choicore.study.springbootjpashop.domain.Delivery;
import me.choicore.study.springbootjpashop.domain.Member;
import me.choicore.study.springbootjpashop.domain.Order;
import me.choicore.study.springbootjpashop.domain.OrderItem;
import me.choicore.study.springbootjpashop.domain.item.Item;
import me.choicore.study.springbootjpashop.repository.ItemRepository;
import me.choicore.study.springbootjpashop.repository.MemberRepository;
import me.choicore.study.springbootjpashop.repository.order.OrderRepository;
import me.choicore.study.springbootjpashop.repository.order.OrderSearch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery(null, null, member.getAddress(), null);

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {

        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 취소
        order.cancel();
    }

    // 검색

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }

}
