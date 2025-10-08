package umc.domain.member.entity.mapping;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.domain.member.entity.Member;
import umc.domain.member.entity.Policy;
import umc.global.entity.BaseEntity;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberPolicy extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne()
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;

    @Column(name = "agreed_at", nullable = false)
    private LocalDateTime agreedAt;

}
