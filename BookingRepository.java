package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
}