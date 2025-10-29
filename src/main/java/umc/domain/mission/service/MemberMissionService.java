package umc.domain.mission.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.mission.dto.GetMemberMissionResponse;
import umc.domain.mission.repository.MemberMissionRepository;

@Service
@RequiredArgsConstructor
public class MemberMissionService {

    private final MemberMissionRepository memberMissionRepository;

    @Transactional(readOnly = true)
    public List<GetMemberMissionResponse> getUserMissions(
            Long userId,
            List<String> statuses,
            LocalDateTime cursorAcceptedAt,
            Long cursorMissionId,
            int limit
    ) {
        return memberMissionRepository.findUserMissionsPage(
                        userId, statuses, cursorAcceptedAt, cursorMissionId, limit
                ).stream()
                .map(p -> new GetMemberMissionResponse(
                        p.getMissionId(),
                        p.getStatus(),
                        p.getPoint(),
                        p.getContent(),
                        p.getStoreName()
                ))
                .toList();
    }

}
