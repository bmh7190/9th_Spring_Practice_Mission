package umc.global.apiPayload.handler;

import jakarta.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umc.global.alert.DiscordWebhookSender;
import umc.global.alert.ErrorEmbed;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.BaseErrorCode;
import umc.global.apiPayload.code.GeneralErrorCode;
import umc.global.apiPayload.exception.GeneralException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GeneralExceptionAdvice {

    private final DiscordWebhookSender discord;

    @Value("${spring.application.name:app}")   private String appName;
    @Value("${spring.profiles.active:default}") private String env;

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(GeneralException ex, HttpServletRequest req) {
        int status = ex.getCode().getStatus().value();
        if (status >= 500) {
            String traceId = UUID.randomUUID().toString().substring(0, 8);
            discord.sendErrorEmbed(
                    new ErrorEmbed(
                            ex,
                            OffsetDateTime.now().toString(),
                            req.getMethod(),
                            req.getRequestURI(),
                            ex.getMessage(),
                            traceId,
                            env,
                            appName
                    ),
                    6
            );
        }
        return ResponseEntity.status(ex.getCode().getStatus())
                .body(ApiResponse.onFailure(ex.getCode(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleAny(Exception ex, HttpServletRequest req) {
        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;

        String traceId = UUID.randomUUID().toString().substring(0, 8);
        discord.sendErrorEmbed(
                new ErrorEmbed(
                        ex,
                        OffsetDateTime.now().toString(),
                        req.getMethod(),
                        req.getRequestURI(),
                        ex.getMessage(),
                        traceId,
                        env,
                        appName
                ),
                6
        );

        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, ex.getMessage()));
    }
}
