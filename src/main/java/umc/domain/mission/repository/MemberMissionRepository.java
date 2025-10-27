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

@Repository
public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    @Query("""
                SELECT new umc.domain.mission.dto.GetMemberMissionResponse(
                    um.mission.id,
                    um.status,
                    ms.point,
                    ms.content,
                    st.name
                )
                FROM UserMission um
                JOIN um.mission ms
                JOIN ms.store st
                WHERE um.user.id = :userId
                  AND um.status IN :statuses
                  AND (
                        um.acceptedAt < :cursorAcceptedAt
                     OR (um.acceptedAt = :cursorAcceptedAt AND um.mission.id < :cursorMissionId)
                  )
                ORDER BY um.acceptedAt DESC, um.mission.id DESC
            """)
    List<GetMemberMissionResponse> findUserMissionsPage(
            @Param("userId") Long userId,
            @Param("statuses") List<String> statuses,
            @Param("cursorAcceptedAt") LocalDateTime cursorAcceptedAt,
            @Param("cursorMissionId") Long cursorMissionId,
            Pageable pageable
    );


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from MemberMission mm where mm.member.id = :memberId")
    int deleteAllByMemberId(@Param("memberId") Long memberId);

}
