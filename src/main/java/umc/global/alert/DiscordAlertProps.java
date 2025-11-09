package umc.global.alert;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "alert.discord")
public record DiscordAlertProps(
        boolean enabled,
        String webhookUrl,
        String username,
        int timeoutSeconds
) {
}