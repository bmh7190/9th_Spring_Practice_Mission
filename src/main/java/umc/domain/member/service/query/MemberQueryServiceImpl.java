package umc.domain.member.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.member.dto.GetMemberResponse;
import umc.domain.member.repository.MemberRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService{

    private final MemberRepository memberRepository;

    public GetMemberResponse getMember(Long memberId) {
        return memberRepository.findMemberInfo(memberId);
    }


}
