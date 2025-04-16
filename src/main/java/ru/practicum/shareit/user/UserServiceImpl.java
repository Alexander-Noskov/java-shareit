package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.addUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User updateUser(Long id, User user) {
        User userToUpdate = userRepository.getUserById(id);
        user.setId(id);
        if (user.getName() == null) {
            user.setName(userToUpdate.getName());
        }
        if (user.getEmail() == null) {
            user.setEmail(userToUpdate.getEmail());
        }
        return userRepository.updateUser(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);
    }
}
