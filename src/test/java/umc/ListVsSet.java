package umc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import umc.domain.member.entity.Member;
import umc.domain.member.entity.mapping.MemberFood;
import umc.domain.member.repository.MemberRepository;

import java.util.HashSet;

@SpringBootTest
public class ListVsSet {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void CountResult() {
        Long memberId = 1L; // 중복 데이터가 들어있는 멤버
        Member m = memberRepository.findWithFoodsById(memberId).orElseThrow();

        int size = m.getFoods().size();

        System.out.println("[List/Set] size = " + size);
        m.getFoods().forEach(mf -> System.out.println(" - " + mf.getFood().getName()));

    }

}
