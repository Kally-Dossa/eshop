package gr.university.eshop.controller;

import gr.university.eshop.dto.ShopLoginDto;
import gr.university.eshop.dto.ShopRegisterDto;
import gr.university.eshop.entity.Shop;
import gr.university.eshop.service.ShopService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    // URL: http://localhost:8080/shop/register
    @PostMapping("/register")
    public String register(@RequestBody ShopRegisterDto dto) {
        try {
            shopService.registerShop(dto);
            return "Registration completed successfully!";
        } catch (Exception e) {
            return "Registration error: " + e.getMessage();
        }
    }

    // URL: http://localhost:8080/shop/login
    @PostMapping("/login")
    public String login(@RequestBody ShopLoginDto loginDto, HttpSession session) {
        try {
            Shop shop = shopService.login(loginDto.getAfm(), loginDto.getPassword());
            session.setAttribute("loggedInShop", shop);
            return "Welcome to the eshop: " + shop.getCompanyName();
        } catch (Exception e) {
            return "Connection error: " + e.getMessage();
        }
    }

    // URL: http://localhost:8080/shop/logout
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "You are logged out.";
    }
}