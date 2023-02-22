package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // 파이널 있는 필드만 가지고 생성자 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */

    @Transactional
    public Long join(Member member){

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
        //디비에 안들어가도 persist 하면 id를 키값으로 저장하고
        //member에 id값도 할당하기 때문에 getId 해도 항상 값이 보장됨
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION

        //조회 안하고 멤버수 세서 그게 0보다 크면 익셉션 던지기
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (! findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }


    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
