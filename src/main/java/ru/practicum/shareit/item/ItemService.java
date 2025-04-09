package ru.practicum.shareit.item;

import java.util.List;

public interface ItemService {
    Item getItemById(Long id);

    List<Item> getItemsByUserId(Long userId);

    List<Item> getItemsBySearchString(String searchString);

    Item createItem(Long userId, Item item);

    Item updateItem(Long userId, Long itemId, Item item);
}
