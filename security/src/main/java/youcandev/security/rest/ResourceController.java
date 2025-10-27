package youcandev.security.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    @GetMapping("/for-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> forAdmin() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("resource for admin");
    }

    @GetMapping("/for-authorized")
    public ResponseEntity<String> forAuthorized() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("resource for authorized");
    }

    @GetMapping("/for-everyone")
    public ResponseEntity<String> forEveryone() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("resource for everyone");
    }
}
