package gr.university.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class FrontEndController {

    //home page home.hmtl
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/viewCart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/viewOrders")
    public String orders() {
        return "orders";
    }
    

}
