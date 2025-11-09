package umc.global.alert;

public record ErrorEmbed(
        Throwable throwable,
        String when,
        String method,
        String path,
        String message,
        String traceId,
        String environment,
        String appName
) {}