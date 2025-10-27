package umc.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ManyToAny;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.inquiry.repository.InquiryRepository;
import umc.domain.member.dto.GetMemberResponse;
import umc.domain.member.entity.Member;
import umc.domain.member.repository.MemberPolicyRepository;
import umc.domain.member.repository.MemberRepository;
import umc.domain.mission.repository.MemberMissionRepository;
import umc.domain.review.repository.ReviewPhotoRepository;
import umc.domain.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final ReviewPhotoRepository reviewPhotoRepository;
    private final ReviewRepository reviewRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberPolicyRepository memberPolicyRepository;
    private final InquiryRepository inquiryRepository;

    @Transactional(readOnly = true)
    public GetMemberResponse getMember(Long memberId) {

        return memberRepository.findMemberInfo(memberId);
    }

    @Transactional
    public void updateMemberName(Long memberId, String name) {
        int updatedCount = memberRepository.updateMemberName(memberId, name);

        if (updatedCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

    }


    @Transactional
    public void deleteMember(Long memberId, boolean hard) {

        if (hard) {
            hardDeleteMember(memberId);
        } else {
            softDeleteMember(memberId);
        }
    }

    private void hardDeleteMember(Long memberId) {

        int deletedPhotos = reviewPhotoRepository.deleteAllByMemberId(memberId);
        int deletedReviews = reviewRepository.deleteAllByMemberId(memberId);
        int deletedMissions = memberMissionRepository.deleteAllByMemberId(memberId);
        int deletedPolicies = memberPolicyRepository.deleteAllByMemberId(memberId);
        int deletedInquiries = inquiryRepository.deleteAllByMemberId(memberId);

        int deletedUsers = memberRepository.hardDeleteById(memberId);

        int total =
                deletedPhotos + deletedReviews + deletedMissions + deletedPolicies + deletedInquiries + deletedUsers;

        if (total == 0) {
            log.info("Member {} delete is idempotent: nothing to delete.", memberId);
        } else {
            log.info("Member {} hard-deleted: photos={}, reviews={}, missions={}, policies={}, inquiries={}, users={}",
                    memberId, deletedPhotos, deletedReviews, deletedMissions, deletedPolicies, deletedInquiries,
                    deletedUsers);
        }
    }

    private void softDeleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        member.softDelete();

    }

}
