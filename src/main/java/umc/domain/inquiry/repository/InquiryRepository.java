package umc.domain.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.domain.inquiry.entity.Inquiry;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry,Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Inquiry i where i.member.id = :memberId")
    int deleteAllByMemberId(@Param("memberId") Long memberId);

}
