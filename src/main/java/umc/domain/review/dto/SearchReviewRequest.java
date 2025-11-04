package umc.domain.review.dto;

public record SearchReviewRequest(
        String storeName,
        Integer starRange
) {
};
