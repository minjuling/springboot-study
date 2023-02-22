package jpabook.jpashop.repository.order.simplequery;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleOrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        //DTO는 엔티티를 참조해도 괜찮음

        this.orderId = orderId;
        this.name = name; // LAZY 초기화됨. 영속성 컨텍스트가 멤버 아이디를 가지고 찾아오는데 없으면 디비 쿼리를 보냄
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        // LAZY 초기화됨.
        // 영속성 컨텍스트가 멤버 아이디를 가지고 찾아오는데 없으면 디비 쿼리를 보냄
    }
}
