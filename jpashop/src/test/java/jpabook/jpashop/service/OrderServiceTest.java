package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional

public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember("회원1");

        Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("상품 주문 시 상품 종류 수가 정확해야함", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야함", 8, book.getStockQuantity());


    }



    @Test
    public void 주문취소() throws Exception{
        // given
        Member member = createMember("이름");
        Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);


        //when
        orderService.cacelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상채는 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문 취소된 상품은 그만큼 재고가 증가해야함", 10, book.getStockQuantity());

    }

    @Test
    public void 상품주문_재고수량초과() throws Exception{
        // given
        Member member = createMember("회원1");

        Book book = createBook("JPA", 10000, 10);

        int orderCount = 11;

        //when


        //then
        NotEnoughStockException thrown = assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), book.getId(), orderCount)); // 예외가 발생해야한다);
        Assertions.assertEquals("need more stock.", thrown.getMessage()); //assert에 fail이 있음

        fail("재고 수량 예외가 발생해야함");

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("서울", "경ㄱㅣ", "123123"));
        em.persist(member);
        return member;
    }
}
