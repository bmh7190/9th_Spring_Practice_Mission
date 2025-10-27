package umc.domain.mission.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetMissionResponse {

    private Long missionId;
    private String content;
    private Integer point;
    private LocalDateTime deadline;
    private String storeName;
    private String storeType;

}
