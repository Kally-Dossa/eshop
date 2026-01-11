package gr.university.eshop.dto;

public class ShopRegisterDto {
    private String afm;
    private String companyName;
    private String owner;
    private String password;

    public ShopRegisterDto() {}

    public ShopRegisterDto(String afm, String companyName, String owner, String password) {
        this.afm = afm;
        this.companyName = companyName;
        this.owner = owner;
        this.password = password;
    }

    // Getters & Setters
    public String getAfm() { return afm; }
    public void setAfm(String afm) { this.afm = afm; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
