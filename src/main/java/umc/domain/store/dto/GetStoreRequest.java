package umc.domain.store.dto;

import java.util.List;
import umc.domain.store.entity.StoreSort;

public record GetStoreRequest(
        Long districtId,
        List<Long> districtIds,
        String keyword,
        StoreSort sort
) {
    public StoreSort safeSort() {
        return sort == null ? StoreSort.latest : sort;
    }
}