package umc.domain.mission.repository.projection;

import java.time.LocalDateTime;

public interface AvailableMissionProjection {

    Long getMissionId();

    String getContent();

    Integer getPoint();

    LocalDateTime getDeadline();

    String getStoreName();

    String getStoreType();
}
