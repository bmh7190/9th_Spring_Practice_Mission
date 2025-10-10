package umc.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.domain.member.entity.mapping.MemberPolicy;

@Repository
public interface MemberPolicyRepository extends JpaRepository<MemberPolicy, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from MemberPolicy mp where mp.member.id = :memberId")
    int deleteAllByMemberId(@Param("memberId") Long memberId);

}
