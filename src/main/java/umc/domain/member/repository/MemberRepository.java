package umc.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
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

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Member m where m.id = :id")
    int hardDeleteById(@Param("id") Long id);

}
