package umc.domain.inquiry.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import umc.domain.member.entity.Member;
import umc.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Inquiry extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InquiryType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InquiryStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InquiryPhoto> inquiryPhotos = new ArrayList<>();

    @Builder
    public Inquiry(
            @NonNull String title,
            @NonNull String content,
            @NonNull InquiryType type,
            InquiryStatus status,
            @NonNull Member member
    ) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.status = (status != null) ? status : InquiryStatus.WAITING;
        this.member = member;
    }
}
