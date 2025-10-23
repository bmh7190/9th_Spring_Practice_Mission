package umc.domain.mission.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import umc.domain.member.entity.Member;
import umc.global.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberMission extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accepted_at", nullable = false)
    private LocalDateTime acceptedAt;

    @Column(name = "success_at", nullable = true)
    private LocalDateTime successAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "mission_status", nullable = false, length = 20)
    private MissionStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public MemberMission(
            LocalDateTime acceptedAt,
            LocalDateTime successAt,
            @NonNull MissionStatus status,
            @NonNull Mission mission,
            @NonNull Member member
    ) {
        this.acceptedAt = (acceptedAt != null) ? acceptedAt : LocalDateTime.now(); // 기본값
        this.successAt = successAt;
        this.status = status;
        this.mission = mission;
        this.member = member;
    }
}
