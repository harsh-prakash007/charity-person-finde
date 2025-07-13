package find_person.com.example.charity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/signup/**",               // All signup flows (OTP, create-password, etc.)
                                "/verify_otp",              // Thymeleaf view mapping
                                "/create_password",         // Thymeleaf view mapping
                                "/login.html",              // Public login page
                                "/signup.html",
                                "/verify_otp.html",
                                "/create_password.html",
                                "/css/**", "/js/**", "/images/**", "/static/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")        // Ensure your form submits to this
                        .usernameParameter("username")       // Name of input field in form
                        .passwordParameter("password")       // Name of password input field
                        .defaultSuccessUrl("/index.html", true)
                        .permitAll()
                );

        return http.build();
    }
}
