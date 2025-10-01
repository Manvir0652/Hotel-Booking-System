package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }


    public boolean cancelBooking(Long bookingId, User user) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null || !booking.getUser().getId().equals(user.getId())) {
            return false; 
        }
        Hotel hotel = booking.getHotel();
        hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
        hotelRepository.save(hotel);
        bookingRepository.delete(booking);
        return true;
    }

    public Booking bookRoom(User user, Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
        if (hotel == null || hotel.getAvailableRooms() <= 0) {
            return null;
        }
        hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
        hotelRepository.save(hotel);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setHotel(hotel);
        booking.setBookingDate(LocalDate.now().toString());
        booking.setPaid(false);

        return bookingRepository.save(booking);
    }
    
    public boolean payForBooking(Long bookingId, User user) {
    Booking booking = bookingRepository.findById(bookingId).orElse(null);
    if (booking != null && booking.getUser().getId().equals(user.getId())) {
        booking.setPaid(true); // just mock payment by marking as paid
        bookingRepository.save(booking);
        return true;
    }
    return false;
}


}