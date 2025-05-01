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

    public List<User> getUserByName(final String name) {
        List<User> all = userRepository.findAll();
        List<User> listByName = all.stream()
                .filter(u -> u.getName().toLowerCase().contains(name))
                .toList();
        return listByName;
    }

    public List<User> getUserByEmail(final String email) {
        List<User> all = userRepository.findAll();
        List<User> listByEmail = all.stream()
                .filter(u -> u.getEmail().toLowerCase().contains(email))
                .toList();
        return listByEmail;
    }

    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByName(final String name) {
        List<User> all = userRepository.findAll();
        List<User> listByName = all.stream().filter(u -> u.getName().equals(name))
                .toList();
        Long id = listByName.get(0).getId();
        userRepository.deleteById(id);
    }

    public User updateUser(final UserDto userDto) {
        List<User> all = userRepository.findAll();
        List<User> listByName = all.stream().filter(u -> u.getName().equals(userDto.getName()))
                .toList();

        User user = listByName.get(0);
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPoints(userDto.getPoints());
        return userRepository.save(user);
    }

}
