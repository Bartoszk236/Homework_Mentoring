package youcandev.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import youcandev.security.jpa.User;
import youcandev.security.jpa.UserRepository;

@Configuration
@RequiredArgsConstructor
public class AdminInit {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${init.admin.password}")
    private String adminPassword;
    @Value("${init.admin.email}")
    private String adminEmail;

    @Bean
    CommandLineRunner seedAdmin() {
        return args -> {
            boolean exist = userRepository.findByEmail(adminEmail).isPresent();
            if (!exist) {
                User user = User.builder()
                        .email(adminEmail)
                        .password(passwordEncoder.encode(adminPassword))
                        .role(User.Role.ADMIN)
                        .build();
                userRepository.save(user);
            }
        };
    }
}
