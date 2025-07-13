package find_person.com.example.charity.controller;
import jakarta.servlet.http.HttpSession;
import find_person.com.example.charity.model.User;
import find_person.com.example.charity.repository.UserRepository;
import find_person.com.example.charity.service.EmailService;
import find_person.com.example.charity.service.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String name,
                          @RequestParam String email,
                          Model model,
                          HttpSession session) {
        String normalizedEmail = email.trim().toLowerCase();
        String otp = otpService.generateOtp(normalizedEmail);
        emailService.sendOtp(normalizedEmail, otp);

        // Store in both model AND session
        model.addAttribute("name", name);
        model.addAttribute("email", normalizedEmail);
        session.setAttribute("name", name);  // Critical addition
        session.setAttribute("email", normalizedEmail);

        return "verify_otp";
    }
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp,
                            Model model,
                            HttpSession session) {

        String normalizedEmail = email.trim().toLowerCase();

        if (otpService.validateOtp(normalizedEmail, otp)) {
            otpService.clearOtp(normalizedEmail);
            // No need to reset session attributes - they're already set
            return "redirect:/create_password";
        }

        model.addAttribute("error", "Invalid OTP");
        model.addAttribute("email", normalizedEmail);
        return "verify_otp";
    }
    @PostMapping("/create-password")
    public String createPassword(@RequestParam String password,
                                 @RequestParam String confirmPassword,
                                 HttpSession session,
                                 Model model) {

        // Retrieve from session
        String name = (String) session.getAttribute("name");
        String email = (String) session.getAttribute("email");

        // Validate passwords
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "create_password";
        }

        // Create and save user
        User user = new User();
        user.setName(name);  // Will now have the correct value
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        // Clear session
        session.removeAttribute("name");
        session.removeAttribute("email");

        return "redirect:/login.html";
    }

}
