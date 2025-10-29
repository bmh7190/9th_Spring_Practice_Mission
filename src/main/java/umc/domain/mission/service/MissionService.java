package umc.domain.mission.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.mission.dto.GetMissionResponse;
import umc.domain.mission.repository.MissionRepository;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

    @Transactional(readOnly = true)
    public List<GetMissionResponse> getAvailableMissions(
            Long userId,
            String address,
            LocalDateTime cursorDeadline,
            Long cursorMissionId,
            int limit
    ) {
        return missionRepository.findAvailableMissionsNative(
                        userId,
                        address,
                        cursorDeadline,
                        cursorMissionId,
                        limit
                ).stream()
                .map(row -> new GetMissionResponse(
                        row.getMissionId(),
                        row.getContent(),
                        row.getPoint(),
                        row.getDeadline(),
                        row.getStoreName(),
                        row.getStoreType()
                ))
                .toList();
    }

}
