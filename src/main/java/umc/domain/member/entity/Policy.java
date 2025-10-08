package umc.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Policy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 300)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_type", nullable = false)
    private PolicyType type;

}
