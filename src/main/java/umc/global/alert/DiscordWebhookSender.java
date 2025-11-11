package umc.global.alert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiscordWebhookSender {

    private final WebClient webClient;
    private final DiscordAlertProps props;

    private static final int COLOR_ERROR = 0xE74C3C;

    public void sendErrorEmbed(ErrorEmbed ctx, int topN) {

        if (!props.enabled() || isBlank(props.webhookUrl())) {
            return;
        }

        Map<String, Object> payload = buildPayload(ctx, topN);

        webClient.post()
                .uri(props.webhookUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .toBodilessEntity()
                .timeout(Duration.ofSeconds(props.timeoutSeconds()))
                .onErrorResume(e -> {
                    log.warn("Discord webhook send failed: {}", e.toString());
                    return Mono.empty();
                }).subscribe();
    }

    private Map<String, Object> buildPayload(ErrorEmbed ctx, int topN) {
        var embed = new LinkedHashMap<String, Object>();
        embed.put("title", "500 Internal Server Error");
        embed.put("description", buildDescription(ctx, topN));
        embed.put("timestamp", Optional.ofNullable(ctx.when()).orElse(OffsetDateTime.now().toString()));
        embed.put("color", 15158332);

        String footerText = joinNonBlank(ctx.environment(), ctx.appName(), " • ");

        if (!isBlank(footerText)) {
            embed.put("footer", Map.of("text", footerText));
        }

        var payload = new HashMap<String, Object>();

        if (!isBlank(props.username())) {
            payload.put("username", props.username());
        }
        payload.put("embeds", List.of(embed));

        return payload;
    }


    private String buildDescription(ErrorEmbed c, int topN) {
        String stackTop = topStack(c.throwable(), topN);
        return """
                **When:** %s
                **Method:** %s
                **Path:** `%s`
                **Status:** 500
                **Exception:** `%s`
                **Message:** %s
                **Trace-Id:** %s
                
                **Stacktrace (top %d):**
                ```%s```
                """.formatted(
                nvl(c.when(), OffsetDateTime.now().toString()),
                nvl(c.method(), "-"),
                nvl(c.path(), "-"),
                c.throwable() != null ? c.throwable().getClass().getName() : "-",
                truncate(nvl(c.message(), "-"), 400),
                nvl(c.traceId(), "-"),
                topN,
                stackTop == null ? "-" : stackTop
        );
    }

    private String topStack(Throwable t, int n) {
        if (t == null) {
            return null;
        }
        var st = t.getStackTrace();
        int limit = Math.min(n, st.length);
        var sb = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            sb.append(st[i]).append('\n');
        }
        return sanitize(sb.toString());
    }

    private String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max) + "…";
    }

    private String nvl(String s, String def) {
        return (s == null || s.isBlank()) ? def : s;
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    private String sanitize(String s) {
        return s == null ? "" : s.replace("```", "`\u200B``").replace("@everyone", "@ everyone");
    }

    private String joinNonBlank(String a, String b, String sep) {
        if (isBlank(a) && isBlank(b)) {
            return "";
        }
        if (isBlank(a)) {
            return b;
        }
        if (isBlank(b)) {
            return a;
        }
        return a + sep + b;
    }
}
