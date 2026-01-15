package gr.university.eshop.controller;

import gr.university.eshop.dto.CitizenRegisterDto;
import gr.university.eshop.dto.LoginDto;
import gr.university.eshop.dto.ProductDto;
import gr.university.eshop.dto.ShopRegisterDto;
import gr.university.eshop.model.Product;
import gr.university.eshop.model.Shop;
import gr.university.eshop.service.ShopService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ShopWebController {

    @Autowired
    private ShopService shopService;

    // --- 1. ΑΡΧΙΚΗ ΣΕΛΙΔΑ (LOGIN & REGISTER) ---
    /*
    @GetMapping("/")
    public String showLoginPage(Model model, HttpSession session) {
        // Αν είναι ήδη συνδεδεμένος, πήγαινέ τον στο dashboard
        if (session.getAttribute("loggedInShop") != null) {
            return "redirect:/shop/dashboard";
        }

        // Στέλνουμε δύο κενά DTOs για να τα "δέσει" το Thymeleaf στις φόρμες
        if (!model.containsAttribute("loginDto")) {
            model.addAttribute("loginDto", new LoginDto());
        }
        if (!model.containsAttribute("registerDto")) {
            model.addAttribute("registerDto", new ShopRegisterDto());
        }

        return "index";
    }
*/
  /*  // --- 2. ΕΠΕΞΕΡΓΑΣΙΑ REGISTRATION (FORM SUBMIT) ---
    @PostMapping("shop/register")
    public String register(@Valid @ModelAttribute("registerDto") ShopRegisterDto dto,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        // Αν υπάρχουν λάθη Validation (π.χ. κενά πεδία, λάθος ΑΦΜ)
        if (result.hasErrors()) {
            model.addAttribute("loginDto", new LoginDto()); // Ξαναβάζουμε το loginDto για να μην σκάσει η σελίδα
            return "index"; // Επιστροφή στο index με τα λάθη εμφανή
        }

        try {
            shopService.registerShop(dto);
            // Flash Attribute: Μήνυμα που θα φανεί ΜΙΑ φορά μετά το redirect
            redirectAttributes.addFlashAttribute("successMessage", "Η εγγραφή ολοκληρώθηκε! Παρακαλώ συνδεθείτε.");
            return "redirect:/"; // Καθαρό redirect για να αδειάσει η φόρμα
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("loginDto", new LoginDto());
            return "index";
        }
    }*/

    // --- 3. ΕΠΕΞΕΡΓΑΣΙΑ LOGIN (FORM SUBMIT) ---
    @PostMapping("shop/login")
    public String login(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                        BindingResult result,
                        HttpSession session,
                        Model model) {

        if (result.hasErrors()) {
            model.addAttribute("registerDto", new ShopRegisterDto());
            return "index";
        }

        try {
            Shop shop = shopService.login(loginDto.getEmail(), loginDto.getPassword());
            session.setAttribute("loggedInShop", shop);
            return "redirect:/shop/dashboard";
        } catch (Exception e) {
            model.addAttribute("loginError", "Λάθος Email ή Κωδικός");
            model.addAttribute("registerDto", new ShopRegisterDto());
            return "index";
        }
    }

    // --- 4. DASHBOARD & ΛΟΙΠΕΣ ΛΕΙΤΟΥΡΓΙΕΣ ---
    @GetMapping("/shop/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) return "redirect:/";

        List<Product> products = shopService.getProductsByShopAfm(loggedInShop.getAfm());
        model.addAttribute("shopName", loggedInShop.getName());
        model.addAttribute("products", products);

        // Only create a new DTO if one wasn't added by a redirect flash attribute (to keep form data if needed)
        if (!model.containsAttribute("newProduct")) {
            model.addAttribute("newProduct", new ProductDto());
        }

        return "dashboard";
    }

    @PostMapping("/shop/products/save")
    public String saveProduct(@ModelAttribute ProductDto productDto,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {

        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) return "redirect:/";

        // --- VALIDATION: Check if Price is negative ---
        if (productDto.getPrice() != null && productDto.getPrice() < 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Το προϊόν δεν αποθηκεύτηκε: Η τιμή δεν μπορεί να είναι αρνητική.");
            return "redirect:/shop/dashboard";
        }

        try {
            shopService.addProductToShop(loggedInShop, productDto);
            redirectAttributes.addFlashAttribute("successMessage", "Το προϊόν προστέθηκε επιτυχώς!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Σφάλμα κατά την αποθήκευση.");
        }

        return "redirect:/shop/dashboard";
    }

    @PostMapping("/shop/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id,
                                HttpSession session, // <--- Add this
                                RedirectAttributes redirectAttributes) {

        // 1. Get Logged in Shop
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) return "redirect:/";

        try {
            // 2. Pass the shop to the service
            shopService.deleteProduct(id, loggedInShop);
            redirectAttributes.addFlashAttribute("successMessage", "Το προϊόν διαγράφηκε.");
        } catch (Exception e) {
            // This catches "Unauthorized" if they don't own the product
            redirectAttributes.addFlashAttribute("errorMessage", "Σφάλμα: " + e.getMessage());
        }

        return "redirect:/shop/dashboard";
    }

    @PostMapping("/shop/products/update-stock")
    public String updateStock(@RequestParam Long productId,
                              @RequestParam Integer newStock,
                              HttpSession session, // <--- Add this
                              RedirectAttributes redirectAttributes) {

        // 1. Get Logged in Shop
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) return "redirect:/";

        // Validation
        if (newStock < 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Η ενημέρωση απέτυχε: Το απόθεμα δεν μπορεί να είναι αρνητικό.");
            return "redirect:/shop/dashboard";
        }

        try {
            // 2. Pass the shop to the service
            shopService.updateProductStock(productId, newStock, loggedInShop);
            redirectAttributes.addFlashAttribute("successMessage", "Το απόθεμα ενημερώθηκε.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Σφάλμα: " + e.getMessage());
        }

        return "redirect:/shop/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}