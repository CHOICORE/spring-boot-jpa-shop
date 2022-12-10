package me.choicore.study.springbootjpashop.service;

import me.choicore.study.springbootjpashop.domain.Member;
import me.choicore.study.springbootjpashop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("회원_가입")
    public void join() throws Exception {

        // given
        Member member = new Member(null, "choicore", null, null);


        // when
        Long savedId = memberService.join(member);

        // then

        assertThat(member).isEqualTo(memberRepository.findOne(savedId));
    }

    @Test
    @DisplayName("중복_회원_예외")
    public void validateDuplicateMember() throws Exception {

        // given
        Member existsMember = new Member(null, "choicore", null, null);
        Member newMember = new Member(null, "choicore", null, null);

        // then
        assertThatThrownBy(() -> {
            // when
            memberService.join(existsMember);
            memberService.join(newMember);
        }).isInstanceOf(IllegalStateException.class);
    }
}