package umc.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.domain.member.service.MemberService;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @DeleteMapping("/{memberId}/hard")
    ResponseEntity<Void>  hardDelete(
            @PathVariable Long memberId,
            @RequestParam(name = "hard", defaultValue = "false") boolean hard
    ){

        memberService.deleteMember(memberId, hard);

        return ResponseEntity.noContent().build();
    }

}
