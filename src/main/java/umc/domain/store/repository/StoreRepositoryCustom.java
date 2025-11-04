package umc.domain.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.domain.store.dto.GetStoreRequest;
import umc.domain.store.dto.GetStoreResponse;

public interface StoreRepositoryCustom {
    Page<GetStoreResponse> searchStores(GetStoreRequest request, Pageable pageable);
}