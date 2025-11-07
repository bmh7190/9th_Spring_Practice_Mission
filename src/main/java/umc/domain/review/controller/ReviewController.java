package umc.domain.review.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.domain.review.dto.SearchReviewRequest;
import umc.domain.review.dto.SearchReviewResponse;
import umc.domain.review.service.query.ReviewQueryService;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.GeneralSuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewQueryService reviewQueryService;

    @GetMapping
    public ApiResponse<List<SearchReviewResponse>> searchReview(Long memberId, SearchReviewRequest request) {

        List<SearchReviewResponse> result = reviewQueryService.searchReview(memberId, request);

        return ApiResponse.onSuccess(GeneralSuccessCode.OK,result);
    }

}
