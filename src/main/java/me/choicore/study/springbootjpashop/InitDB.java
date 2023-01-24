package me.choicore.study.springbootjpashop;


import lombok.RequiredArgsConstructor;
import me.choicore.study.springbootjpashop.domain.*;
import me.choicore.study.springbootjpashop.domain.item.Book;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {


    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA", "서울", "1", "11111");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20_000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10_000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20_000, 2);

            Delivery delivery = createDelivery(member);


            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);

        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = Delivery.builder()
                    .address(member.getAddress())
                    .build();
            return delivery;
        }


        public void dbInit2() {
            Member member = createMember("userB", "진주", "2", "22222");
            em.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 10_000, 100);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 20_000, 100);

            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10_000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20_000, 2);

            Delivery delivery = createDelivery(member);


            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);

        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = Member.builder()
                    .name(name)
                    .address(new Address(city, street, zipcode))
                    //.orders(new ArrayList<>())
                    .build();
            return member;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }
    }

}
