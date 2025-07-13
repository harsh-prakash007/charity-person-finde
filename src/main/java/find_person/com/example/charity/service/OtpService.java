package find_person.com.example.charity.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {
    private final Map<String, String> otpStorage = new HashMap<>();
    private final Random random = new Random();

    public String generateOtp(String email) {
        String otp = String.format("%06d", random.nextInt(999999));
        otpStorage.put(email.trim().toLowerCase(), otp);

        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        return otp.equals(otpStorage.get(email.trim().toLowerCase()));
    }

    public void clearOtp(String email) {
        otpStorage.remove(email.trim().toLowerCase());
    }

}