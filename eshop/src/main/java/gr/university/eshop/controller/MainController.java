package gr.university.eshop.controller;

import gr.university.eshop.dto.CitizenRegisterDto;
import gr.university.eshop.dto.LoginDto;
import gr.university.eshop.dto.ShopRegisterDto;
import gr.university.eshop.model.Citizen;
import gr.university.eshop.model.Shop;
import gr.university.eshop.service.CitizenService;
import gr.university.eshop.service.ShopService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class MainController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private CitizenService citizenService;

    // --- 1. LANDING PAGE (Αρχική) ---
    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("loggedInShop") != null) return "redirect:/shop/dashboard";
        if (session.getAttribute("loggedInUser") != null) return "redirect:/user/dashboard";
        return "index";
    }

    // --- 2. LOGIN PAGE ---
    @GetMapping("/login-page")
    public String showLoginPage(Model model, HttpSession session) {
        if (session.getAttribute("loggedInShop") != null) return "redirect:/shop/dashboard";
        if (session.getAttribute("loggedInUser") != null) return "redirect:/";

        model.addAttribute("loginDto", new LoginDto());
        return "login"; // Φορτώνει το login.html
    }

    // --- 3. REGISTER PAGE (Με Tabs) ---
    @GetMapping("/register-page")
    public String showRegisterPage(Model model) {
        model.addAttribute("shopRegisterDto", new ShopRegisterDto());
        model.addAttribute("userRegisterDto", new CitizenRegisterDto());
        model.addAttribute("activeTab", "user"); // Default tab
        return "register"; // Φορτώνει το register.html
    }

    // --- POST: LOGIN ---
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                        BindingResult result, HttpSession session, Model model) {
        if (result.hasErrors()) {
            return "login"; // Επιστροφή στο login.html αν υπάρχουν κενά πεδία
        }

        try {
            Citizen citizen = citizenService.login(loginDto.getEmail(), loginDto.getPassword());
            session.setAttribute("loggedInUser", citizen);
            // Redirect στο User Dashboard
            return "redirect:/user/dashboard";
        } catch (Exception e1) {
            try {
                Shop shop = shopService.login(loginDto.getEmail(), loginDto.getPassword());
                session.setAttribute("loggedInShop", shop);
                return "redirect:/shop/dashboard";
            } catch (Exception e2) {
                model.addAttribute("loginError", "Λάθος Email ή Κωδικός");
                return "login"; // Επιστροφή στο login.html
            }
        }
    }

    // --- POST: SHOP REGISTER ---
    @PostMapping("/shop/register")
    public String registerShop(@Valid @ModelAttribute("shopRegisterDto") ShopRegisterDto dto,
                               BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("userRegisterDto", new CitizenRegisterDto());
            model.addAttribute("activeTab", "shop");
            return "register"; // Επιστροφή στο register.html
        }

        try {
            Shop newShop = shopService.registerShop(dto);
            session.setAttribute("loggedInShop", newShop);
            return "redirect:/shop/dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("userRegisterDto", new CitizenRegisterDto());
            model.addAttribute("activeTab", "shop");
            return "register";
        }
    }

    // --- POST: USER REGISTER ---
    @PostMapping("/user/register")
    public String registerUser(@Valid @ModelAttribute("userRegisterDto") CitizenRegisterDto dto,
                               BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("shopRegisterDto", new ShopRegisterDto());
            model.addAttribute("activeTab", "user");
            return "register";
        }

        try {
            Citizen newCitizen = citizenService.registerCitizen(dto);

            // 2. Αποθήκευση στο Session
            session.setAttribute("loggedInUser", newCitizen);


            System.out.println("User registered and logged in: " + newCitizen.getEmail());
            return "redirect:/user/dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("shopRegisterDto", new ShopRegisterDto());
            model.addAttribute("activeTab", "user");
            return "register";
        }
    }
}