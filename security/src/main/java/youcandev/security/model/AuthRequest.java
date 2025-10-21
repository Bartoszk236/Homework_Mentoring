package youcandev.security.model;

public record AuthRequest(
        String email,
        String password
) {
}
