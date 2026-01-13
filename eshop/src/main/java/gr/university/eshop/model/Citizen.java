package gr.university.eshop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "citizens")
public class Citizen {

    @Id
    @Column(length = 9, nullable = false, unique = true)
    private String afm;

    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;

    public Citizen() {
    }

    public Citizen(String afm, String name, String surname, String email, String password, String role) {
        this.afm = afm;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

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

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}