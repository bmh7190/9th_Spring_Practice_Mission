package umc.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import umc.domain.review.entity.ReviewPhoto;

@Repository
public interface ReviewPhotoRepository extends JpaRepository<ReviewPhoto, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        delete from Review r
         where r.member.id = :memberId
    """)
    int deleteAllByMemberId(@org.springframework.data.repository.query.Param("memberId") Long memberId);

}
