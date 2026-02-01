package gr.university.eshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ShopRegisterDto {
    @NotBlank(message = "Το ΑΦΜ είναι υποχρεωτικό")
    @Pattern(regexp = "^\\d{9}$", message = "Το ΑΦΜ πρέπει να έχει 9 ψηφία")
    private String afm;

    @NotBlank(message = "Το Όνομα είναι υποχρεωτικό")
    @Size(min = 2, max = 50, message = "Το όνομα πρέπει να έχει απο 2 έως 50 χαρακτήρες.")
    private String name;

    @NotBlank(message = "Το Email είναι υποχρεωτικό")
    @Email(message = "Μη έγκυρο Email")
    private String email;

    @NotBlank(message = "Ο κωδικός είναι υποχρεωτικός")
    @Size(min = 4, message = "Ο κωδικός πρέπει να είναι τουλάχιστον 4 χαρακτήρες")
    private String password;

    public ShopRegisterDto() {
    }

    public ShopRegisterDto(String afm, String name,String email, String password) {

        this.afm = afm;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAfm() { return afm; }
    public void setAfm(String afm) { this.afm = afm; }
}