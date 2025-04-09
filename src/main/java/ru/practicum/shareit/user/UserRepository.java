package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<Long, User> users;

    private long id;

    public UserRepository() {
        users = new HashMap<>();
        id = 1;
    }

    public User addUser(User user) {
        checkEmail(user);
        user.setId(id);
        users.put(user.getId(), user);
        id++;
        return user;
    }

    public User getUserById(Long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("User with id " + id + " not found");
        }
        return users.get(id);
    }

    public User updateUser(User user) {
        checkEmail(user);
        users.put(user.getId(), user);
        return user;
    }

    public void deleteUserById(Long id) {
        users.remove(id);
    }

    private void checkEmail(User user) {
        List<String> emails = users.values().stream()
                .filter(u -> !u.getId().equals(user.getId()))
                .map(User::getEmail)
                .toList();
        if (emails.contains(user.getEmail())) {
            throw new ConflictException("User with email " + user.getEmail() + " already exists");
        }
    }
}
