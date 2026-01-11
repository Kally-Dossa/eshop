package gr.university.eshop.dto;

public class ShopLoginDto {

    private String afm;
    private String password;

    public ShopLoginDto() {}

    public String getAfm() { return afm; }
    public void setAfm(String afm) { this.afm = afm; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
