package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.User;
import com.example.hotelbooking.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
        if ("ADMIN".equals(user.getRole())) {
            return "redirect:/admin"; // admin panel
        } else {
            return "redirect:/"; // normal user homepage
        }
    }    


          
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("email") String email,
                           @RequestParam("name") String name,
                           @RequestParam("role") String role,
                           Model model) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setName(name);
        user.setRole(role);
        userService.register(user);
        model.addAttribute("message", "Registration successful! Please login.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


    @GetMapping("/profile")
public String profilePage(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
        return "redirect:/login";
    }
    model.addAttribute("user", user);
    return "profile"; // the Thymeleaf template profile.html
}
    @PostMapping("/profile")
public String updateProfile(@RequestParam("username") String username,
                            @RequestParam(value = "password", required = false) String password,
                            @RequestParam("email") String email,
                            @RequestParam("name") String name,
                            HttpSession session,
                            Model model) {

    User user = (User) session.getAttribute("user");
    if (user == null) {
        return "redirect:/login";
    }

    // Update fields
    user.setUsername(username);
    if (password != null && !password.isEmpty()) {
        user.setPassword(password); // Ideally, encode password
    }
    user.setEmail(email);
    user.setName(name);

    userService.updateUser(user); // save changes in DB

    session.setAttribute("user", user); // update session
    model.addAttribute("user", user);
    model.addAttribute("message", "Profile updated successfully!");
    return "profile";
}

}
