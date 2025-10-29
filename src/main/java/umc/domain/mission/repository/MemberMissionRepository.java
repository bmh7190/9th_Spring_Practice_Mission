package umc.domain.mission.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.domain.mission.dto.GetMemberMissionResponse;
import umc.domain.mission.entity.MemberMission;
import umc.domain.mission.repository.projection.MemberMissionProjection;

@Repository
public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    @Query(value = """
            SELECT
                ms.id          AS missionId,
                um.status      AS status,
                ms.point       AS point,
                ms.content     AS content,
                st.name        AS storeName
            FROM user_mission um
            JOIN mission ms ON um.mission_id = ms.id
            JOIN store st   ON ms.store_id   = st.id
            WHERE um.user_id = :userId
              AND um.status IN (:statuses)
              AND (
                    um.accepted_at < :cursorAcceptedAt
                 OR (um.accepted_at = :cursorAcceptedAt AND ms.id < :cursorMissionId)
              )
            ORDER BY um.accepted_at DESC, ms.id DESC
            LIMIT :limit
            """,
            nativeQuery = true)
    List<MemberMissionProjection> findUserMissionsPage(
            @Param("userId") Long userId,
            @Param("statuses") List<String> statuses,
            @Param("cursorAcceptedAt") LocalDateTime cursorAcceptedAt,
            @Param("cursorMissionId") Long cursorMissionId,
            @Param("limit") int limit
    );


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from MemberMission mm where mm.member.id = :memberId")
    int deleteAllByMemberId(@Param("memberId") Long memberId);

}
