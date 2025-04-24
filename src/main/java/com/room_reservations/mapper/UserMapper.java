package com.room_reservations.mapper;

import com.room_reservations.domain.User;
import com.room_reservations.domain.UserDto;
import com.room_reservations.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    private final UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User mapToUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail(), userDto.getPoints());
    }

    public UserDto mapToUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPoints());
    }

    public List<UserDto> mapToUserDtoList(final List<User> users) {
        List<UserDto> userDtoList = users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
        return userDtoList;
    }

}
