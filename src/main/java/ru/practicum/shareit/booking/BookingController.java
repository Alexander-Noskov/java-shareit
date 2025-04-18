package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto create(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
                             @RequestBody @Valid CreateBookingDto createBookingDto) {
        return bookingMapper.toBookingDto(bookingService.addBooking(userId, bookingMapper.toBooking(createBookingDto)));
    }

    @PatchMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingDto approve(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
                              @PathVariable Long bookingId,
                              @RequestParam("approved") Boolean value) {
        return bookingMapper.toBookingDto(bookingService.approveBooking(userId, bookingId, value));
    }

    @GetMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingDto getBooking(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
                                 @PathVariable Long bookingId) {
        return bookingMapper.toBookingDto(bookingService.getBooking(userId, bookingId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDto> getBookingsByBookerId(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
                                                  @RequestParam(value = "state", defaultValue = "ALL", required = false) String state) {
        return bookingService.getBookingsByBookerId(userId, state).stream()
                .map(bookingMapper::toBookingDto)
                .toList();
    }

    @GetMapping("/owner")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDto> getBookingsByOwnerId(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
                                                 @RequestParam(value = "state", defaultValue = "ALL", required = false) String state) {
        return bookingService.getBookingsByOwnerId(userId, state).stream()
                .map(bookingMapper::toBookingDto)
                .toList();
    }
}
