package gr.university.eshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CitizenRegisterDto {

    @NotBlank(message = "Το ΑΦΜ είναι υποχρεωτικό")
    @Pattern(regexp = "^\\d{9}$", message = "Το ΑΦΜ πρέπει να έχει 9 ψηφία")
    private String afm;

    @NotBlank(message = "Το Όνομα είναι υποχρεωτικό")
    private String name;

    @NotBlank(message = "Το Επίθετο είναι υποχρεωτικό")
    private String surname; // ΝΕΟ

    @NotBlank(message = "Το Email είναι υποχρεωτικό")
    @Email(message = "Μη έγκυρο Email")
    private String email;

    @NotBlank(message = "Ο κωδικός είναι υποχρεωτικός")
    @Size(min = 4, message = "Ο κωδικός πρέπει να είναι τουλάχιστον 4 χαρακτήρες")
    private String password;

    // Getters & Setters
    public String getAfm() { return afm; }
    public void setAfm(String afm) { this.afm = afm; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}