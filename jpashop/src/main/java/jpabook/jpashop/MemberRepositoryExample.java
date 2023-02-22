package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryExample {
    @PersistenceContext // 이 어노테이션이 있으면 스프링부트가 엔티티 매니저를 주입해줌
    private EntityManager em; // 스프링부트에서 자동으로 엔티티매니저 다 자동으로 생성해줌

    public Long save(MemberExample member){
        em.persist(member);
        return member.getId(); // 커맨드랑 쿼리를 분리하기 위해 다음에 조회할떄를 대비해 리턴값을 ID 값 정도로만 함
    }

    public MemberExample find(Long id){
        return em.find(MemberExample.class, id);
    }
}
