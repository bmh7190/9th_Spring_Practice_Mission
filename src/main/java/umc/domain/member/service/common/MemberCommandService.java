package umc.domain.member.service.common;

public interface MemberCommandService {
    void updateMemberName(Long memberId, String name);
    void deleteMember(Long memberId, boolean hard);
}
