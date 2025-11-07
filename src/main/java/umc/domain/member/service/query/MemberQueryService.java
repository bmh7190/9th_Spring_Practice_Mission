package umc.domain.member.service.query;

import umc.domain.member.dto.GetMemberResponse;

public interface MemberQueryService {
    GetMemberResponse getMember(Long memberId);
}
