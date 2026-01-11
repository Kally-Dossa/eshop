package gr.university.eshop.controller;

import gr.university.eshop.dto.LoginDto;
import gr.university.eshop.dto.ShopRegisterDto;
// import gr.university.eshop.DTO.UserRegisterDto; // Υποθέτω ότι έχεις αυτό το DTO
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController { // Μπορείς να το βάλεις και στον ShopWebController

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        // Αν είναι ήδη συνδεδεμένος κάποιος, κάνε redirect (προαιρετικά)
        if (session.getAttribute("loggedInShop") != null) return "redirect:/shop/dashboard";
        // if (session.getAttribute("loggedInUser") != null) return "redirect:/user/dashboard";

        // 1. Shop DTOs
        if (!model.containsAttribute("shopLoginDto")) model.addAttribute("shopLoginDto", new LoginDto());
        if (!model.containsAttribute("shopRegisterDto")) model.addAttribute("shopRegisterDto", new ShopRegisterDto());

        // 2. User DTOs (Πρέπει να φτιάξεις τα αντίστοιχα DTOs αν δεν τα έχεις)
        if (!model.containsAttribute("userLoginDto")) model.addAttribute("userLoginDto", new LoginDto());

        if (!model.containsAttribute("userRegisterDto")) model.addAttribute("userRegisterDto", new ShopRegisterDto());

        return "index";
    }
}