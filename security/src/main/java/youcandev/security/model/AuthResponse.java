package youcandev.security.model;

public record AuthResponse(
        String token,
        String email
) {
}
