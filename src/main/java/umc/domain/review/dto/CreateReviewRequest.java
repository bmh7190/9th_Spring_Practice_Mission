package umc.domain.review.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import umc.domain.review.entity.ReviewPhoto;

@Getter
public class CreateReviewRequest {

    private Long memberId;
    private Long storeId;
    private String content;
    private BigDecimal star;
    private List<String> photoUrls; 

}
