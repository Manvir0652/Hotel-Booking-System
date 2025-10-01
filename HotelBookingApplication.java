package com.example.hotelbooking;

import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.repository.HotelRepository;
import com.example.hotelbooking.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelBookingApplication implements CommandLineRunner {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(HotelBookingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // ------------------ Admin setup ------------------
        if (!userRepository.existsByUsername("manvir singh")) {
            User admin = new User();
            admin.setUsername("manvir singh");
            admin.setPassword("manvir123"); // encode password in production
            admin.setName("manvir singh");
            admin.setEmail("manvirsingh@gmail.com");
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }

        // ------------------ Hotels setup ------------------
        Object[][] hotels = {
            {"Grand Palace", "Delhi", 10},
            {"Sea View", "Goa", 5},
            {"Mountain Inn", "Manali", 8},
            {"City Lights", "Mumbai", 12},
            {"Sunset Resort", "Kerala", 7},
            {"Royal Heritage", "Jaipur", 6},
            {"Lakeside Retreat", "Udaipur", 9},
            {"Desert Oasis", "Jaisalmer", 4},
            {"Forest Haven", "Coorg", 8},
            {"Blue Lagoon", "Andaman", 5},
            {"Snow Peak", "Shimla", 10},
            {"Golden Sands", "Pondicherry", 6},
            {"Hilltop Hideaway", "Mussoorie", 7},
            {"Riverfront Inn", "Rishikesh", 9},
            {"Urban Stay", "Bangalore", 11}
        };

        for (Object[] h : hotels) {
            String name = (String) h[0];
            String location = (String) h[1];
            int rooms = (int) h[2];

            if (!hotelRepository.hotelExists(name)) {
                hotelRepository.save(new Hotel(null, name, location, rooms));
            }
        }
    }
}
