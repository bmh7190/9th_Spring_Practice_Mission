package umc.domain.review.service.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.review.dto.SearchReviewRequest;
import umc.domain.review.dto.SearchReviewResponse;
import umc.domain.review.entity.QReview;
import umc.domain.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;
    private static final QReview review = QReview.review;

    public List<SearchReviewResponse> searchReview(Long memberId, SearchReviewRequest request) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(review.member.id.eq(memberId));

        if (request.storeName() != null && !request.storeName().isBlank()) {
            builder.and(review.store.name.containsIgnoreCase(request.storeName()));
        }

        if (request.starRange() != null) {
            builder.and(starBucket(request.starRange()));
        }

        return reviewRepository.searchReview(builder);
    }

    private BooleanExpression starBucket(int bucket) {

        QReview r = QReview.review;

        BigDecimal min = BigDecimal.valueOf(bucket).setScale(1);

        if (bucket == 5) {
            return r.star.eq(BigDecimal.valueOf(5.0).setScale(1));
        }

        BigDecimal maxExclusive = BigDecimal.valueOf(bucket + 1).setScale(1);

        return r.star.goe(min).and(r.star.lt(maxExclusive));
    }

}
