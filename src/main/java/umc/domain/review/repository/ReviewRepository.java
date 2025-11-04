package umc.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.domain.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Review r where r.member.id = :memberId")
    int deleteAllByMemberId(@Param("memberId") Long memberId);


}
