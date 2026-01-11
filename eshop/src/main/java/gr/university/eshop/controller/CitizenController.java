package gr.university.eshop.controller;

import gr.university.eshop.entity.Citizen;
import gr.university.eshop.service.CitizenService;
import gr.university.eshop.dto.CitizenRegisterDto;
import gr.university.eshop.dto.LoginDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // Means this class responds with data (not HTML yet)
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    // --- REGISTRATION ---
    // URL: http://localhost:8080/register
    @PostMapping("/register")
    public String register(@RequestBody CitizenRegisterDto dto) {
        try {
            citizenService.registerCitizen(dto);
            return "Registration completed successfully!";
        } catch (Exception e) {
            return "Registration error: " + e.getMessage();
        }
    }

    // --- LOGIN ---
    // URL: http://localhost:8080/login
    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto, HttpSession session) {
        try {
            Citizen citizen = citizenService.login(loginDto.getEmail(), loginDto.getPassword());

            // WE KEEP THE USER IN MEMORY (SESSION)
            session.setAttribute("loggedInUser", citizen);

            return "Welcome " + citizen.getName();
        } catch (Exception e) {
            return "Connection error: " + e.getMessage();
        }
    }

    // --- LOGOUT ---
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // clear memory
        return "You are logged out.";
    }
}