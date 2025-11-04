package umc.domain.review.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SearchReviewResponse(

        Long reviewId,
        String storeName,
        BigDecimal star,
        String content,
        LocalDateTime createdAt

) {
}
