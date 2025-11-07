package umc.global.apiPayload;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record PageResponse<T>(
        List<T> content,
        PageMeta page
) {
    public static <T> PageResponse<T> of(Page<T> page) {
        return PageResponse.<T>builder()
                .content(page.getContent())
                .page(PageMeta.of(page))
                .build();
    }
}