package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.user.UserMapper;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    public Booking toBooking(CreateBookingDto createBookingDto) {
        if (createBookingDto == null) {
            return null;
        }
        return Booking.builder()
                .start(Timestamp.valueOf(createBookingDto.getStart()))
                .end(Timestamp.valueOf(createBookingDto.getEnd()))
                .item(Item.builder().id(createBookingDto.getItemId()).build())
                .build();
    }

    public BookingDto toBookingDto(Booking booking) {
        if (booking == null) {
            return null;
        }
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart().toLocalDateTime())
                .end(booking.getEnd().toLocalDateTime())
                .status(booking.getStatus())
                .item(itemMapper.toItemDto(booking.getItem()))
                .booker(userMapper.toUserDto(booking.getBooker()))
                .build();
    }
}
