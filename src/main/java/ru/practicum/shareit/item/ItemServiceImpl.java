package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDtoWithComments;
import ru.practicum.shareit.user.UserService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemMapper itemMapper;
    private final CommentMapper commentMapper;

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item with id " + id + " not found"));
    }

    @Override
    public ItemDtoWithComments getItemWithCommentsById(Long id) {
        Item item = getItemById(id);
        List<CommentDto> comments = commentRepository.findAllByItemId(id).stream()
                .map(commentMapper::toCommentDto)
                .toList();
        return ItemDtoWithComments.builder()
                .itemDto(itemMapper.toItemDto(item))
                .comments(comments)
                .build();
    }

    @Override
    public List<Item> getItemsByUserId(Long userId) {
        return itemRepository.findByOwnerId(userId);
    }

    @Override
    public List<Item> getItemsBySearchString(String searchString) {
        if (searchString.isEmpty()) {
            return new ArrayList<>();
        }
        return itemRepository.findByNameContainingIgnoreCaseAndAvailable(searchString, true);
    }

    @Override
    public Item createItem(Long userId, Item item) {
        item.setOwner(userService.getUserById(userId));
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Long userId, Long itemId, Item item) {
        Item itemToUpdate = getItemById(itemId);

        if (!itemToUpdate.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Item not found");
        }
        if (item.getAvailable() != null) {
            itemToUpdate.setAvailable(item.getAvailable());
        }

        if (item.getDescription() != null) {
            itemToUpdate.setDescription(item.getDescription());
        }

        if (item.getName() != null) {
            itemToUpdate.setName(item.getName());
        }

        return itemRepository.save(itemToUpdate);
    }

    @Override
    public Comment addComment(Long userId, Long itemId, Comment comment) {
        Booking booking = bookingRepository.findAllByItemIdAndBookerId(itemId, userId).stream()
                .findFirst()
                .orElseThrow(() -> new ValidException("User with id " + userId + " did not use item with id " + itemId));

        comment.setCreated(Timestamp.from(Instant.now()));

        if (!comment.getCreated().after(booking.getEnd())) {
            throw new ValidException("Comment created before booking end");
        }

        comment.setItem(booking.getItem());
        comment.setUser(booking.getBooker());

        return commentRepository.save(comment);
    }
}
