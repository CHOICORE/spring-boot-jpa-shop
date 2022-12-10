package me.choicore.study.springbootjpashop.service;

import me.choicore.study.springbootjpashop.domain.item.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    EntityManager em;


    @Test
    @DisplayName("상품_수정")
    public void updateTest() throws Exception {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Book book = em.find(Book.class, 1L);
            
            book.setName("JPA");

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }
}