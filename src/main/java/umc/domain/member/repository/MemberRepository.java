package umc.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.domain.member.dto.GetMemberResponse;
import umc.domain.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("""
                select distinct m
                from Member m
                left join fetch m.foods mf
                left join fetch mf.food f
                where m.id = :id
            """)
    Optional<Member> findWithFoodsById(@Param("id") Long id);

    @Query("""
                    select new umc.domain.member.dto.GetMemberResponse(m.name, m.email, m.point, m.phoneNumber)
                    from Member m WHERE m.id = :memberId
            """)
    GetMemberResponse findMemberInfo(@Param("memberId") Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.name = :name where m.id = :memberId")
    int updateMemberName(@Param("memberId") Long memberId, @Param("name") String name);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Member m where m.id = :id")
    int hardDeleteById(@Param("id") Long id);

}
