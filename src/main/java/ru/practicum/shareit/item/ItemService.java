package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDtoWithComments;

import java.util.List;

public interface ItemService {
    Item getItemById(Long id);

    ItemDtoWithComments getItemWithCommentsById(Long id);

    List<Item> getItemsByUserId(Long userId);

    List<Item> getItemsBySearchString(String searchString);

    Item createItem(Long userId, Item item);

    Item updateItem(Long userId, Long itemId, Item item);

    Comment addComment(Long userId, Long itemId, Comment comment);
}
