package test.flyway.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Sql("/data/test-member-data.sql")
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        final Member member1 = getMember("name1");
        final Member member2 = getMember("name2");
        memberRepository.saveAll(List.of(member1, member2));

        final List<Member> members = memberRepository.findAll();

        assertThat(members).hasSize(5);
    }

    @Test
    void findAll() {
        final List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(3)
            .extracting("name", "contact")
            .containsExactlyInAnyOrder(
                tuple("name1", "000-0000-0000"),
                tuple("name2", "111-1111-1111"),
                tuple("name3", "222-2222-2222")
            );
    }

    private Member getMember(final String name) {
        return Member.builder()
            .name(name)
            .email("abc@abc.com")
            .password("1234")
            .contact("000-0000-0000")
            .build();
    }
}