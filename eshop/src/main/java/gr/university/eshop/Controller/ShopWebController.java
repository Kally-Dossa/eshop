package gr.university.eshop.Controller;

import gr.university.eshop.Entity.Product;
import gr.university.eshop.Entity.Shop;
import gr.university.eshop.Service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ShopWebController {

    @Autowired
    private ShopService shopService;

    // --- 1. Display Dashboard with Products AND the empty object for the Popup ---
    @GetMapping("/shop/dashboard")
    public String dashboard(Model model) {
        try {
            String myShopAfm = "123456789";
            Shop shop = shopService.findShopByAfm(myShopAfm);

            // List of existing products
            model.addAttribute("products", shop.getProducts());


            model.addAttribute("newProduct", new Product());
            model.addAttribute("shopName", shop.getName());
            return "shop_dashboard";
        } catch (Exception e) {
            return "error";
        }
    }

    // --- 2. Save  ---
    @PostMapping("/shop/products/save")
    public String saveProduct(@ModelAttribute("newProduct") Product product) {
        try {
            String myShopAfm = "123456789";
            shopService.addProductToShop(myShopAfm, product);
            return "redirect:/shop/dashboard"; // Returns to the dashboard
        } catch (Exception e) {
            return "error";
        }
    }

    // --- 3. Update Stock  ---
    @PostMapping("/shop/products/update-stock")
    public String updateStock(@RequestParam("productId") Long productId,
                              @RequestParam("newStock") Integer newStock) {
        try {
            shopService.updateProductStock(productId, newStock);
            return "redirect:/shop/dashboard";
        } catch (Exception e) {
            return "error";
        }
    }
}