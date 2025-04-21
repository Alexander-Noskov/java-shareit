package ru.practicum.shareit.booking;

import java.util.List;

public interface BookingService {
    Booking addBooking(Long userId, Booking booking);

    Booking approveBooking(Long userId, Long bookingId, Boolean value);

    Booking getBooking(Long userId, Long bookingId);

    List<Booking> getBookingsByBookerId(Long userId, String state);

    List<Booking> getBookingsByOwnerId(Long userId, String state);
}
