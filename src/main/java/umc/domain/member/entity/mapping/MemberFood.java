package umc.domain.member.entity.mapping;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.domain.member.entity.Food;
import umc.domain.member.entity.Member;
import umc.global.entity.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
}
