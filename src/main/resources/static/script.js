<script>
document.addEventListener("DOMContentLoaded", function () {

    // ========== SEND OTP HANDLER ==========
    const otpForm = document.getElementById("otpForm");
    if (otpForm) {
        otpForm.addEventListener("submit", function (e) {
            e.preventDefault(); // prevent default form submit

            const name = document.getElementById("name").value;
            const email = document.getElementById("email").value;

            fetch("/signup/send-otp", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: `name=${encodeURIComponent(name)}&email=${encodeURIComponent(email)}`
            })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url; // redirect to verify_otp.html
                } else {
                    return response.text().then(data => {
                        document.getElementById("otpResponse").innerText = "OTP sent!";
                    });
                }
            })
            .catch(error => {
                document.getElementById("otpResponse").innerText = "Failed to send OTP.";
                console.error("Error sending OTP:", error);
            });
        });
    }

function previewImage(event) {
    const input = event.target;
    const previewContainer = document.getElementById("imagePreview");
    const previewImage = document.getElementById("uploadedImage");

    if (input.files && input.files[0]) {
        const reader = new FileReader();

        reader.onload = function (e) {
            previewImage.src = e.target.result;
            previewImage.style.display = "block";
            previewContainer.style.background = "none";
        };

        reader.readAsDataURL(input.files[0]);
    }
}
