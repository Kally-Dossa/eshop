package gr.university.eshop.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDto {

    @NotBlank(message = "Το Email είναι υποχρεωτικό")
    @Email(message = "Μη έγκυρη μορφή Email")
    private String email;

    @NotBlank(message = "Ο κωδικός είναι υποχρεωτικός")
    private String password;

    public LoginDto() {
    }

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters και Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}