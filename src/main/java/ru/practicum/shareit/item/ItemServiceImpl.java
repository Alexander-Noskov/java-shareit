package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Item getItemById(Long id) {
        return itemRepository.getItemById(id);
    }

    @Override
    public List<Item> getItemsByUserId(Long userId) {
        return itemRepository.getItemsByUserId(userId);
    }

    @Override
    public List<Item> getItemsBySearchString(String searchString) {
        if (searchString.isEmpty()) {
            return new ArrayList<>();
        }
        return itemRepository.getItemsBySearchString(searchString).stream()
                .filter(Item::getAvailable)
                .toList();
    }

    @Override
    public Item createItem(Long userId, Item item) {
        item.setOwner(userRepository.getUserById(userId));
        return itemRepository.createItem(item);
    }

    @Override
    public Item updateItem(Long userId, Long itemId, Item item) {
        Item itemToUpdate = itemRepository.getItemById(itemId);

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

        return itemRepository.updateItem(itemToUpdate);
    }
}
