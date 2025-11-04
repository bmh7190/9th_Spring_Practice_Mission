package umc.domain.store.dto;

import java.time.LocalDateTime;

public record GetStoreResponse(
        Long storeId,
        String name,
        String districtName,
        LocalDateTime createdAt
) {}