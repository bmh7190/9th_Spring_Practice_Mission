package umc.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.domain.mission.entity.MemberMission;

@Repository
public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from MemberMission mm where mm.member.id = :memberId")
    int deleteAllByMemberId(@Param("memberId") Long memberId);

}
