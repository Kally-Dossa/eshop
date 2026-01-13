package gr.university.eshop.controller;

import gr.university.eshop.model.Citizen;
import gr.university.eshop.service.CitizenService;
import gr.university.eshop.dto.CitizenRegisterDto;
import gr.university.eshop.dto.LoginDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    // --- REGISTRATION ---
    @PostMapping("/register")
    public String register(@RequestBody CitizenRegisterDto dto) {
        try {
            citizenService.registerCitizen(dto);
            return "Η σύνδεση ολοκληρώθηκε με επιτυχία!";
        } catch (Exception e) {
            return "Σφάλμα εγγραφής: " + e.getMessage();
        }
    }

    // --- LOGIN ---
    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto, HttpSession session) {
        try {
            Citizen citizen = citizenService.login(loginDto.getEmail(), loginDto.getPassword());
            session.setAttribute("loggedInUser", citizen);
            return "Καλωσήρθες " + citizen.getName();
        } catch (Exception e) {
            return "Σφάλμα σύνδεσης: " + e.getMessage();
        }
    }

    // --- LOGOUT ---
    // Νέο URL: POST /api/user/logout
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Έχετε αποδυνδεθεί.";
    }
}