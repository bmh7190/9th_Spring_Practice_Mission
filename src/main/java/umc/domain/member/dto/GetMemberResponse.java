package umc.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class GetMemberResponse {

    private final String name;
    private final String email;
    private final Integer point;
    private final String phoneNumber;

}
