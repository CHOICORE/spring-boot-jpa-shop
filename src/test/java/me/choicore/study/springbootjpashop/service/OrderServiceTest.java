package me.choicore.study.springbootjpashop.service;

import me.choicore.study.springbootjpashop.domain.Address;
import me.choicore.study.springbootjpashop.domain.Member;
import me.choicore.study.springbootjpashop.domain.Order;
import me.choicore.study.springbootjpashop.domain.OrderStatus;
import me.choicore.study.springbootjpashop.domain.item.Book;
import me.choicore.study.springbootjpashop.exception.NotEnoughStockException;
import me.choicore.study.springbootjpashop.repository.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;


    @Test
    @DisplayName("상품_주문")
    public void order() throws Exception {

        // given

        Member member = createMember();

        Book book = createBook("JPA", 10_000, 10);

        int orderCount = 2;
        // when
        Long savedId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order savedOrder = orderRepository.findOne(savedId);

        assertThat(savedOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(savedOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(savedOrder.getTotalPrice()).isEqualTo(10_000 * orderCount);
        assertThat(book.getStockQuantity()).isEqualTo(8);
    }


    @Test
    @DisplayName("상품_주문_재고_수량_초과")
    public void NotEnoughStockException() throws Exception {

        // given
        Member member = createMember();
        Book book = createBook("JPA", 10_000, 10);

        int orderCount = 11;

        // then
        assertThatThrownBy(() -> {
            // when
            orderService.order(member.getId(), book.getId(), orderCount);
        }).isInstanceOf(NotEnoughStockException.class)
                .hasMessageContaining("재고가 부족합니다.");
    }


    @Test
    @DisplayName("주문_취소")
    public void cancel() throws Exception {
        // given
        Member member = createMember();
        Book book = createBook("JPA", 10_000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);
        // then

        Order savedOrder = orderRepository.findOne(orderId);
        assertThat(savedOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);

        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member(null, "choicore", new Address("서울", "구로동", "12345"), new ArrayList<>());
        em.persist(member);
        return member;
    }
}