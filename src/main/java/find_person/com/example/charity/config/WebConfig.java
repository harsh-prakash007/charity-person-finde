package find_person.com.example.charity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/verify_otp").setViewName("verify_otp");
        registry.addViewController("/create_password").setViewName("create_password");
    }
}
