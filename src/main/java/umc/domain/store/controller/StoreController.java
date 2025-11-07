package umc.domain.store.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.domain.store.dto.GetStoreRequest;
import umc.domain.store.dto.GetStoreResponse;
import umc.domain.store.service.StoreQueryService;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.PageResponse;
import umc.global.apiPayload.code.GeneralSuccessCode;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreQueryService storeQueryService;

    @GetMapping
    public ApiResponse<PageResponse<GetStoreResponse>> searchStores(
            GetStoreRequest request,
            @PageableDefault(sort = "latest") Pageable pageable
    ) {

        Page<GetStoreResponse> result = storeQueryService.searchStores(request, pageable);

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, PageResponse.of(result));
    }
}