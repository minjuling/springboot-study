package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.simplequery.SimpleOrderQueryDto;
import jpabook.jpashop.repository.order.simplequery.SimpleOrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * X to One (ManyToOne, OneToOne)관계에서 성능 최적화 하는 방법
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final SimpleOrderQueryRepository simpleOrderRepository;

    /**
     * V1. 엔티티 직접 노출
     * - Hibernate5Module 모듈 등록, LAZY=null 처리 * - 양방향 관계 문제 발생 -> @JsonIgnore
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기환
             }
            return all;
        }
    @GetMapping("api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2(){
        return orderRepository.findAllByString(new OrderSearch()).stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
                .map(o-> new SimpleOrderDto(o))
                .collect(Collectors.toList());
    }

    @GetMapping("api/v4/simple-orders")
    public List<SimpleOrderQueryDto> orderV4(){ // 의존관계가 한쪽으로 흘러야해서 새로운 리포지토리 만듬. orderRepository 안에 안만들고..
        return simpleOrderRepository.findOrderDtos();

    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화됨.
            // 영속성 컨텍스트가 멤버 아이디를 가지고 찾아오는데 없으면 디비 쿼리를 보냄
            orderDate = order.getOrderDateTime();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            // LAZY 초기화됨.
            // 영속성 컨텍스트가 멤버 아이디를 가지고 찾아오는데 없으면 디비 쿼리를 보냄
        }


    }


}