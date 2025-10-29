package umc.domain.mission.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.domain.mission.entity.Mission;
import umc.domain.mission.repository.projection.AvailableMissionProjection;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query(value = """
            SELECT
                ms.id           AS missionId,
                ms.content      AS content,
                ms.point        AS point,
                ms.deadline     AS deadline,
                st.name         AS storeName,
                st.type         AS storeType
            FROM mission ms
            JOIN store st ON ms.store_id = st.id
            WHERE st.address LIKE CONCAT('%', :address, '%')
              AND ms.deadline >= CURRENT_TIMESTAMP
              AND NOT EXISTS (
                    SELECT 1
                    FROM user_mission um
                    WHERE um.user_id = :userId
                      AND um.mission_id = ms.id
                      AND um.status IN ('IN_PROGRESS', 'SUCCESS')
              )
              AND (
                    ms.deadline > :cursorDeadline
                 OR (ms.deadline = :cursorDeadline AND ms.id < :cursorMissionId)
              )
            ORDER BY ms.deadline ASC, ms.id DESC
            LIMIT :limit
            """,
            nativeQuery = true)
    List<AvailableMissionProjection> findAvailableMissionsNative(
            @Param("userId") Long userId,
            @Param("address") String address,
            @Param("cursorDeadline") LocalDateTime cursorDeadline,
            @Param("cursorMissionId") Long cursorMissionId,
            @Param("limit") int limit
    );


}
