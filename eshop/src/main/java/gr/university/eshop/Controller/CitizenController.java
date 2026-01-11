package gr.university.eshop.controller;

import gr.university.eshop.model.Citizen;
import gr.university.eshop.service.CitizenService;
import gr.university.eshop.dto.CitizenRegisterDto;
import gr.university.eshop.dto.LoginDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user") // <--- ΠΡΟΣΘΗΚΗ: Όλα τα URLs θα ξεκινάνε με /api/user
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    // --- REGISTRATION ---
    // Νέο URL: POST /api/user/register
    @PostMapping("/register") // Απλοποίηση του URL (το /api/user μπαίνει αυτόματα)
    public String register(@RequestBody CitizenRegisterDto dto) {
        try {
            citizenService.registerCitizen(dto);
            return "Registration completed successfully!";
        } catch (Exception e) {
            return "Registration error: " + e.getMessage();
        }
    }

    // --- LOGIN ---
    // Νέο URL: POST /api/user/login
    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto, HttpSession session) {
        try {
            Citizen citizen = citizenService.login(loginDto.getEmail(), loginDto.getPassword());
            session.setAttribute("loggedInUser", citizen);
            return "Welcome " + citizen.getName();
        } catch (Exception e) {
            return "Connection error: " + e.getMessage();
        }
    }

    // --- LOGOUT ---
    // Νέο URL: POST /api/user/logout
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "You are logged out.";
    }
}