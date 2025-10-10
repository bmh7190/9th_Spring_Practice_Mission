package umc.domain.member.entity.mapping;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import umc.domain.member.entity.Food;
import umc.domain.member.entity.Member;
import umc.global.entity.BaseEntity;

import java.util.concurrent.atomic.AtomicInteger;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Slf4j
public class MemberFood extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    private Long memberIdOrNull() { return (member != null ? member.getId() : null); }
    private Long foodIdOrNull()   { return (food   != null ? food.getId()   : null); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberFood other)) return false;
        return java.util.Objects.equals(memberIdOrNull(), other.memberIdOrNull())
                && java.util.Objects.equals(foodIdOrNull(),   other.foodIdOrNull());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(memberIdOrNull(), foodIdOrNull());
    }

    @PostLoad
    private void onLoad() {
        System.out.println("[LOAD] MemberFood entity loaded -> id=" + id + ", food=" + (food != null ? food.getName() : "null"));
    }
}
