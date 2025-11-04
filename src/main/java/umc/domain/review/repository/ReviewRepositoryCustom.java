package umc.domain.review.repository;

import com.querydsl.core.types.Predicate;
import java.util.List;
import umc.domain.review.dto.SearchReviewResponse;

public interface ReviewRepositoryCustom {

    List<SearchReviewResponse> searchReview(Predicate predicate);
}
