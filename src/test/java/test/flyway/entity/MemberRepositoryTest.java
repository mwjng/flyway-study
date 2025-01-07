package test.flyway.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        final Member member1 = getMember("name1");
        final Member member2 = getMember("name2");
        memberRepository.saveAll(List.of(member1, member2));

        final List<Member> members = memberRepository.findAll();

        assertThat(members).hasSize(2)
            .extracting("name", "email")
            .containsExactlyInAnyOrder(
                tuple("name1", "abc@abc.com"),
                tuple("name2", "abc@abc.com")
            );
    }

    private Member getMember(final String name) {
        Member member = Member.builder()
            .name(name)
            .email("abc@abc.com")
            .password("1234")
            .contact("000-0000-0000")
            .build();
        return member;
    }
}