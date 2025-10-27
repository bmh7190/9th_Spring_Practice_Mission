package umc.domain.mission.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.domain.mission.dto.GetMissionResponse;
import umc.domain.mission.entity.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("""
                SELECT new umc.domain.mission.dto.GetAvailableMissionResponse(
                    ms.id,
                    ms.content,
                    ms.point,
                    ms.deadline,
                    st.name,
                    st.type
                )
                FROM Mission ms
                JOIN ms.store st
                WHERE st.address LIKE %:address%
                  AND ms.deadline >= CURRENT_TIMESTAMP
                  AND NOT EXISTS (
                        SELECT 1
                        FROM UserMission um
                        WHERE um.user.id = :userId
                          AND um.mission.id = ms.id
                          AND um.status IN ('IN_PROGRESS', 'SUCCESS')
                  )
                  AND (
                        ms.deadline > :cursorDeadline
                     OR (ms.deadline = :cursorDeadline AND ms.id < :cursorMissionId)
                  )
                ORDER BY ms.deadline ASC, ms.id DESC
            """)
    List<GetMissionResponse> findAvailableMissions(
            @Param("userId") Long userId,
            @Param("address") String address,
            @Param("cursorDeadline") LocalDateTime cursorDeadline,
            @Param("cursorMissionId") Long cursorMissionId,
            Pageable pageable
    );


}
