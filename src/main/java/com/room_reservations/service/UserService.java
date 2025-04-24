package com.room_reservations.service;

import com.room_reservations.domain.User;
import com.room_reservations.domain.UserDto;
import com.room_reservations.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(final User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(final Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(final UserDto userDto) {
        User user1 = userRepository.findById(userDto.getId()).orElse(null);
        user1.setName(userDto.getName());
        user1.setEmail(userDto.getEmail());
        user1.setPoints(userDto.getPoints());
        return userRepository.save(user1);
    }

}
