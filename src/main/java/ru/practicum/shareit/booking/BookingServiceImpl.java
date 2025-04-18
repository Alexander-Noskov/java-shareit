package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    public Booking addBooking(Long userId, Booking booking) {
        Item item = itemService.getItemById(booking.getItem().getId());
        User user = userService.getUserById(userId);
        if (!booking.getStart().before(booking.getEnd())) {
            throw new ValidException("Start date must be before end date");
        }
        if (!item.getAvailable()) {
            throw new ValidException("Item is not available");
        }

        booking.setItem(item);
        booking.setBooker(user);
        booking.setStatus(BookingStatus.WAITING);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking approveBooking(Long userId, Long bookingId, Boolean value) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking with id " + bookingId + " not found"));
        User owner = booking.getItem().getOwner();
        if (!owner.getId().equals(userId)) {
            throw new ValidException("User with id " + userId + " is not owner of item");
        }
        if (value) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBooking(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking with id " + bookingId + " not found"));
        User booker = booking.getBooker();
        User owner = booking.getItem().getOwner();
        if (!owner.getId().equals(userId) && !booker.getId().equals(userId)) {
            throw new ValidException("User with id " + userId + " is not owner or booker of item");
        }
        return booking;
    }

    @Override
    public List<Booking> getBookingsByBookerId(Long userId, String state) {
        userService.getUserById(userId);
        return switch (state) {
            case "ALL" -> bookingRepository.findAllByBookerId(userId);
            case "CURRENT" -> bookingRepository.findAllByBookerIdAndCurrentTimestamp(userId);
            case "PAST" -> bookingRepository.findAllByBookerIdAndPastTimestamp(userId);
            case "FUTURE" -> bookingRepository.findAllByBookerIdAndFutureTimestamp(userId);
            case "WAITING" -> bookingRepository.findAllByBookerIdAndStatus(userId, BookingStatus.WAITING);
            case "REJECTED" -> bookingRepository.findAllByBookerIdAndStatus(userId, BookingStatus.REJECTED);
            default -> throw new ValidException("Invalid state");
        };
    }

    @Override
    public List<Booking> getBookingsByOwnerId(Long userId, String state) {
        userService.getUserById(userId);
        return switch (state) {
            case "ALL" -> bookingRepository.findAllByOwnerId(userId);
            case "CURRENT" -> bookingRepository.findAllByOwnerIdAndCurrentTimestamp(userId);
            case "PAST" -> bookingRepository.findAllByOwnerIdAndPastTimestamp(userId);
            case "FUTURE" -> bookingRepository.findAllByOwnerIdAndFutureTimestamp(userId);
            case "WAITING" -> bookingRepository.findAllByOwnerIdAndStatus(userId, BookingStatus.WAITING);
            case "REJECTED" -> bookingRepository.findAllByOwnerIdAndStatus(userId, BookingStatus.REJECTED);
            default -> throw new ValidException("Invalid state");
        };
    }
}
