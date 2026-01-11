package gr.university.eshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import gr.university.eshop.dto.CheckoutResponseDto;
import gr.university.eshop.dto.OrderDto;
import gr.university.eshop.model.Citizen;
import gr.university.eshop.service.OrderService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/checkout")
    public CheckoutResponseDto checkout(HttpSession session) throws Exception {
        Citizen citizen = getCitizenFromSession(session);
        return orderService.checkout(citizen);
    }


    @GetMapping("/history")
    public List<OrderDto> myOrders(HttpSession session) {
        Citizen citizen = getCitizenFromSession(session);
        return orderService.getCitizenOrders(citizen);
    }

    private Citizen getCitizenFromSession(HttpSession session) {
        
         /* TODO
        String afm = (String) session.getAttribut("CITIZEN_AFM");
        String role = (String) session.getAttribut("ROLE");

        if(afm == null || !"CITIZEN".equals(role)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } 
        
        return citizenRepository.findById(afm)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Citizen not found")
        );

        */

        //TODO maybe do not store in session CItizen object but email and Role (Citizen or shop)
        return (Citizen)session.getAttribute("loggedInUser");
    }
}
