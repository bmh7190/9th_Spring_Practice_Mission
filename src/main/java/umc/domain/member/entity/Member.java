package umc.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.domain.mission.entity.MemberMission;
import umc.global.entity.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

}
