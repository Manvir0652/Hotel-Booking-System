package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.repository.HotelRepository;
import com.example.hotelbooking.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String adminDashboard(HttpSession session, Model model) {
        User admin = (User) session.getAttribute("user");
        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            return "redirect:/";
        }
        model.addAttribute("admin", admin);
        model.addAttribute("hotels", hotelRepository.findAll());
        return "admin-dashboard";
    }

    @PostMapping("/hotels/add")
    public String addHotel(@RequestParam("name") String name,
                           @RequestParam("location") String location,
                           @RequestParam("availableRooms") int availableRooms) {
        hotelRepository.save(new Hotel(null, name, location, availableRooms));
        return "redirect:/admin";
    }

    @PostMapping("/hotels/delete")
    public String deleteHotel(@RequestParam("id") Long id) {
        hotelRepository.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/hotels/edit/{id}")
    public String editHotelForm(@PathVariable Long id, Model model) {
        model.addAttribute("hotel", hotelRepository.findById(id).orElse(null));
        return "edit-hotel"; // separate page for editing
    }

    @PostMapping("/hotels/edit")
    public String editHotel(@RequestParam("id") Long id,
                            @RequestParam("name") String name,
                            @RequestParam("location") String location,
                            @RequestParam("availableRooms") int availableRooms) {
        Hotel hotel = hotelRepository.findById(id).orElse(null);
        if (hotel != null) {
            hotel.setName(name);
            hotel.setLocation(location);
            hotel.setAvailableRooms(availableRooms);
            hotelRepository.save(hotel);
        }
        return "redirect:/admin";
    }
     
     @GetMapping("/profile/edit")
    public String editAdminProfileForm(HttpSession session, Model model) {
        User admin = (User) session.getAttribute("user");
        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            return "redirect:/";
        }
        model.addAttribute("admin", admin);
        return "admin-edit-profile"; // template for editing admin info
    }

    @PostMapping("/profile/update")
    public String updateAdminProfile(@ModelAttribute("admin") User updatedAdmin, HttpSession session) {
        User admin = (User) session.getAttribute("user");
        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            return "redirect:/";
        }

        // Update admin details
        admin.setName(updatedAdmin.getName());
        admin.setEmail(updatedAdmin.getEmail());
        admin.setPassword(updatedAdmin.getPassword()); // encode if needed

        // Save to DB
        userRepository.save(admin);

        // Update session
        session.setAttribute("user", admin);

        return "redirect:/admin";
    }
}


