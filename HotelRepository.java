package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT COUNT(h) > 0 FROM Hotel h WHERE h.name = :name")
    boolean hotelExists(@Param("name") String name);
}