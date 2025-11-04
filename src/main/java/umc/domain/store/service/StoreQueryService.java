package umc.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.store.dto.GetStoreRequest;
import umc.domain.store.dto.GetStoreResponse;
import umc.domain.store.repository.StoreRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreQueryService {

    private final StoreRepository storeRepository;

    public Page<GetStoreResponse> searchStores(GetStoreRequest request, Pageable pageable) {
        return storeRepository.searchStores(request, pageable);
    }
}