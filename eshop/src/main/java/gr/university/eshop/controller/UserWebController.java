package gr.university.eshop.controller;

import gr.university.eshop.dto.GetCartDetailsDto;
import gr.university.eshop.dto.OrderDto;
import gr.university.eshop.model.Citizen;
import gr.university.eshop.model.Product;
import gr.university.eshop.repository.ProductRepository;
import gr.university.eshop.service.CartService;
import gr.university.eshop.service.OrderService;
import gr.university.eshop.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserWebController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    // --- 1. USER DASHBOARD (Προϊόντα, Καλάθι, Ιστορικό) ---
    @GetMapping("/dashboard")
    public String dashboard(
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String brand,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size,
        HttpSession session,
        Model model
    ) {
        Citizen citizen = (Citizen) session.getAttribute("loggedInUser");
        if (citizen == null) return "redirect:/login-page";

        try {
            
            Page<Product> products = productService.search(type, brand, description, minPrice, maxPrice, page, size);


            model.addAttribute("products", products.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", products.getTotalPages());

            model.addAttribute("size", size);

            // 2. Δεδομένα Καλαθιού
            GetCartDetailsDto cartDetails = cartService.getCart(citizen);
            model.addAttribute("cart", cartDetails);

            // 3. Ιστορικό Παραγγελιών
            List<OrderDto> orders = orderService.getCitizenOrders(citizen);
            model.addAttribute("orders", orders);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Σφάλμα φόρτωσης δεδομένων: " + e.getMessage());
        }

        return "user-dashboard";
    }

    // --- 2. ΠΡΟΣΘΗΚΗ ΣΤΟ ΚΑΛΑΘΙ ---
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        Citizen citizen = (Citizen) session.getAttribute("loggedInUser");
        if (citizen == null) return "redirect:/login-page";

        try {
            cartService.addItem(citizen, productId, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Το προϊόν προστέθηκε στο καλάθι!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Σφάλμα: " + e.getMessage());
        }
        return "redirect:/user/dashboard";
    }

    // --- 3. ΑΦΑΙΡΕΣΗ ΑΠΟ ΤΟ ΚΑΛΑΘΙ ---
    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        Citizen citizen = (Citizen) session.getAttribute("loggedInUser");
        if (citizen != null) {
            try {
                cartService.deleteItem(citizen, productId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/user/dashboard";
    }

    // --- 4. CHECKOUT ---
    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirectAttributes) {
        Citizen citizen = (Citizen) session.getAttribute("loggedInUser");
        if (citizen == null) return "redirect:/login-page";

        try {
            orderService.checkout(citizen);
            redirectAttributes.addFlashAttribute("successMessage", "Η παραγγελία ολοκληρώθηκε επιτυχώς!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Αποτυχία παραγγελίας: " + e.getMessage());
        }
        return "redirect:/user/dashboard";
    }

    // search with JpaSpecification
    @GetMapping("/search")
    public String searchProducts(
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String brand,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(required = false) Integer size,
        RedirectAttributes redirectAttributes
    ) {
        try {
            redirectAttributes.addAttribute("type", type);
            redirectAttributes.addAttribute("brand", brand);
            redirectAttributes.addAttribute("description", description);
            redirectAttributes.addAttribute("minPrice", minPrice);
            redirectAttributes.addAttribute("maxPrice", maxPrice);

            if (size != null) {
                redirectAttributes.addAttribute("size", size);
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Αποτυχία αναζήτησης: " + e.getMessage());
        }
        return "redirect:/user/dashboard";
    }
    
}