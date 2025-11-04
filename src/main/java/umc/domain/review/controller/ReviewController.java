package umc.domain.review.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.domain.review.dto.SearchReviewRequest;
import umc.domain.review.dto.SearchReviewResponse;
import umc.domain.review.service.ReviewQueryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewQueryService reviewQueryService;

    @GetMapping
    public List<SearchReviewResponse> searchReview(Long memberId, SearchReviewRequest request) {
        return reviewQueryService.searchReview(memberId, request);
    }

}
