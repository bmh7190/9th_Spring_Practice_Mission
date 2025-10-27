package umc.domain.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.member.entity.Member;
import umc.domain.member.repository.MemberRepository;
import umc.domain.review.dto.CreateReviewRequest;
import umc.domain.review.entity.Review;
import umc.domain.review.entity.ReviewPhoto;
import umc.domain.review.repository.ReviewRepository;
import umc.domain.store.entity.Store;
import umc.domain.store.repository.StoreRepository;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void createReview(CreateReviewRequest request) {

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));

        Review review = Review.builder()
                .store(store)
                .content(request.getContent())
                .star(request.getStar())
                .member(member)
                .build();

        List<ReviewPhoto> photos = request.getPhotoUrls().stream()
                .map(url -> ReviewPhoto.builder()
                        .photoUrl(url)
                        .build())
                .toList();

        review.addPhotos(photos);

        reviewRepository.save(review);

    }

}
