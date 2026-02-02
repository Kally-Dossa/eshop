package gr.university.eshop.controller;
import org.springframework.data.domain.Page;

import gr.university.eshop.dto.ProductDto;
import gr.university.eshop.model.Product;
import gr.university.eshop.model.Shop;
import gr.university.eshop.service.ShopService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ShopWebController {

    @Autowired
    private ShopService shopService;


    // --- DASHBOARD & ΛΟΙΠΕΣ ΛΕΙΤΟΥΡΓΙΕΣ ---
    @GetMapping("/shop/dashboard")
    public String showDashboard(HttpSession session,
                                Model model,
                                @RequestParam(defaultValue = "0") int page) {
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) return "redirect:/";

        int pageSize = 10;

        Page<Product> productPage = shopService.getProductsByShopAfmPaginated(loggedInShop.getAfm(), page, pageSize);

        model.addAttribute("shopName", loggedInShop.getName());
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

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
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) return "redirect:/";

        try {
            shopService.deleteProduct(id, loggedInShop);
            redirectAttributes.addFlashAttribute("successMessage", "Το προϊόν διαγράφηκε.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Σφάλμα: " + e.getMessage());
        }

        return "redirect:/shop/dashboard";
    }

    @PostMapping("/shop/products/update-stock")
    public String updateStock(@RequestParam Long productId,
                              @RequestParam Integer newStock,
                              HttpSession session,
                              @RequestParam(defaultValue = "0") int page,
                              RedirectAttributes redirectAttributes) {

        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) return "redirect:/";

        try {
            shopService.updateProductStock(productId, newStock, loggedInShop);
            redirectAttributes.addFlashAttribute("successMessage", "Το απόθεμα ενημερώθηκε.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/shop/dashboard?page=" + page;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}