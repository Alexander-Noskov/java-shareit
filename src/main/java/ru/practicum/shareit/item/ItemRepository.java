package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private final Map<Long, Item> items;

    private long id;

    public ItemRepository() {
        items = new HashMap<>();
        id = 1;
    }

    public Item createItem(Item item) {
        item.setId(id);
        id++;
        items.put(item.getId(), item);
        return item;
    }

    public Item getItemById(Long id) {
        if (!items.containsKey(id)) {
            throw new NotFoundException("Item with id " + id + " not found");
        }
        return items.get(id);
    }

    public List<Item> getItemsByUserId(Long userId) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId().equals(userId))
                .toList();
    }

    public List<Item> getItemsBySearchString(String searchString) {
        return items.values().stream()
                .filter(item -> item.getName().toLowerCase().contains(searchString.toLowerCase()))
                .toList();
    }

    public Item updateItem(Item item) {
        items.put(item.getId(), item);
        return item;
    }
}
