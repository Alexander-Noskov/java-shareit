package ru.practicum.shareit.user;

public interface UserService {
    User addUser(User user);

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUserById(Long id);
}
