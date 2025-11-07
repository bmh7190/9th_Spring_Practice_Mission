package umc.domain.review.service.command;

import static umc.domain.member.exception.code.MemberErrorCode.NOT_FOUND_MEMBER;
import static umc.domain.store.exception.code.StoreErrorCode.NOT_FOUND_STORE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.member.entity.Member;
import umc.domain.member.exception.MemberException;
import umc.domain.member.repository.MemberRepository;
import umc.domain.review.dto.CreateReviewRequest;
import umc.domain.review.entity.Review;
import umc.domain.review.entity.ReviewPhoto;
import umc.domain.review.repository.ReviewRepository;
import umc.domain.store.entity.Store;
import umc.domain.store.exception.StoreException;
import umc.domain.store.repository.StoreRepository;

@RequiredArgsConstructor
@Service
public class ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void createReview(CreateReviewRequest request) {

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));

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
