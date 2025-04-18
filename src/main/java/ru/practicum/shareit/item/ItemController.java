package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithComments;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Validated
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final CommentMapper commentMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
                              @RequestBody @Valid ItemDto itemDto) {
        return itemMapper.toItemDto(itemService.createItem(userId, itemMapper.toItem(itemDto)));
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDtoWithComments getItemWithCommentsById(@PathVariable Long itemId) {
        return itemService.getItemWithCommentsById(itemId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getItemsByUserId(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId) {
        return itemService.getItemsByUserId(userId).stream()
                .map(itemMapper::toItemDto)
                .toList();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> searchItemsByText(@RequestParam("text") String text) {
        return itemService.getItemsBySearchString(text).stream()
                .map(itemMapper::toItemDto)
                .toList();
    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
                              @PathVariable Long itemId,
                              @RequestBody ItemDto itemDto) {
        return itemMapper.toItemDto(itemService.updateItem(userId, itemId, itemMapper.toItem(itemDto)));
    }

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId,
                                 @PathVariable Long itemId,
                                 @RequestBody @Valid CreateCommentDto createCommentDto) {
        return commentMapper.toCommentDto(itemService.addComment(userId, itemId, commentMapper.toComment(createCommentDto)));
    }
}
