package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;
    // PersistenceContext annotation이 있으면
    // 스프링이 만든 엔티티매니저를 여기에 주입시킴

    @PersistenceUnit //엔티티 매니저 팩토리를 직접 주입 받을 수 있음
    private EntityManagerFactory emf;



    public void save(Member member){
        em.persist(member); //JPA가 member를 저장하는 로직
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
        //단건 조회, (첫번째 타입, 두번째 pk)
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
        //JPQL을 작성해야함. JPQL의 대상은 엔티티
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
