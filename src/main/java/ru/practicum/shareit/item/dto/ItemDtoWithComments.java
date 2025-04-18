package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoWithComments {
    @JsonUnwrapped
    private ItemDto itemDto;

    private List<CommentDto> comments;

    private LocalDateTime nextBooking;

    private LocalDateTime lastBooking;
}
