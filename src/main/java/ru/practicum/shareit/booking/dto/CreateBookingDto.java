package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingDto {
    @NotNull(message = "Item id cannot be empty")
    private Long itemId;

    @NotNull(message = "Start cannot be empty")
    @FutureOrPresent(message = "Start cannot be past")
    private LocalDateTime start;

    @NotNull(message = "End cannot be empty")
    @Future(message = "End cannot be present or past")
    private LocalDateTime end;
}
