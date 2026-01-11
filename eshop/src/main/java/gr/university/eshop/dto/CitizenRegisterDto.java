package gr.university.eshop.dto;

public class CitizenRegisterDto {
    private String name;
    private String email;
    private String password;
    private Long afm;

    public CitizenRegisterDto() {
    }

    public CitizenRegisterDto(String name, String email, String password, Long afm) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.afm = afm;
    }

    // Getters και Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Long getAfm() { return afm; }
    public void setAfm(Long afm) { this.afm = afm; }
}
