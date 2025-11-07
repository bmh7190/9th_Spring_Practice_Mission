package umc.domain.review.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import umc.domain.review.dto.SearchReviewResponse;
import umc.domain.review.entity.QReview;
import umc.domain.store.entity.QStore;

@RequiredArgsConstructor
public class ReviewQueryDslImpl implements ReviewQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SearchReviewResponse> searchReview(Predicate predicate) {
        QReview review = QReview.review;
        QStore store = QStore.store;

        return queryFactory
                .select(Projections.constructor(SearchReviewResponse.class,
                        review.id,
                        store.name,
                        review.star,
                        review.content,
                        review.createdAt
                ))
                .from(review)
                .join(review.store, store)
                .where(predicate)
                .orderBy(review.createdAt.desc())
                .fetch();
    }

}
