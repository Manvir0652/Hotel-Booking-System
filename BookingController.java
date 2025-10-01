package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/bookings")
    public String viewBookings(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Booking> bookings = bookingService.getBookingsByUser(user);
        model.addAttribute("bookings", bookings);
        model.addAttribute("user", user);
        return "bookings";
    }

    @PostMapping("/book")
    public String bookRoom(@RequestParam("hotelId")Long hotelId, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Booking booking = bookingService.bookRoom(user, hotelId);
        if (booking != null) {
            redirectAttributes.addFlashAttribute("message", "Room booked successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Booking failed. No rooms available.");
        }
        return "redirect:/hotels";
    }

   @PostMapping("/cancel")
public String cancelBooking(@RequestParam ("bookingId")Long bookingId, HttpSession session, RedirectAttributes redirectAttributes) {
    User user = (User) session.getAttribute("user");
    System.out.println("Cancel request: bookingId=" + bookingId + ", user=" + user);

    if (user == null) {
        return "redirect:/login";
    }

    boolean success = false;
    try {
        success = bookingService.cancelBooking(bookingId, user);
        System.out.println("Cancellation success: " + success);
    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", "Cancellation failed: " + e.getMessage());
    }

    if (success) {
        redirectAttributes.addFlashAttribute("message", "Booking cancelled successfully.");
    } else {
        redirectAttributes.addFlashAttribute("error", "Cancellation failed.");
    }

    return "redirect:/bookings";
}

@PostMapping("/pay")
public String payBooking(@RequestParam("bookingId") Long bookingId,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
        return "redirect:/login";
    }

    boolean success = bookingService.payForBooking(bookingId, user);
    if (success) {
        redirectAttributes.addFlashAttribute("message", "Payment successful! Your booking is now marked as paid.");
    } else {
        redirectAttributes.addFlashAttribute("error", "Payment failed. Please try again.");
    }

    return "redirect:/bookings";
}




}
