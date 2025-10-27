package umc.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class GetMemberMissionResponse {

    private final Long missionId;
    private final String status;
    private final Integer point;
    private final String content;
    private final String storeName;

}
