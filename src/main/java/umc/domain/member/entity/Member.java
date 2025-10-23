package umc.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import umc.domain.member.entity.mapping.MemberFood;
import umc.domain.mission.entity.MemberMission;
import umc.global.entity.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberGender gender;

    @Column(nullable = true)
    private LocalDate birth;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private Integer point;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 30)
    private SocialType socialType;

    @Column(nullable = true, length = 100)
    private String socialUid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MemberType memberType;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MemberStatus memberStatus;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberMission> missions = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberFood> foods = new HashSet<>();

    public void softDelete() {
        this.memberStatus = MemberStatus.WITHDRAWN;
        this.setDeletedAt(LocalDateTime.now());
    }

    @Builder
    public Member(
            String name,
            @NonNull MemberGender gender,
            LocalDate birth,
            @NonNull String address,
            @NonNull String email,
            Integer point,
            SocialType socialType,
            String socialUid,
            @NonNull MemberType memberType,
            @NonNull String phoneNumber,
            MemberStatus memberStatus
    ) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.address = address;
        this.email = email;
        this.point = (point != null) ? point : 0;
        this.socialType = socialType;
        this.socialUid = socialUid;
        this.memberType = memberType;
        this.phoneNumber = phoneNumber;
        this.memberStatus = (memberStatus != null) ? memberStatus : MemberStatus.ACTIVE;
    }

}
