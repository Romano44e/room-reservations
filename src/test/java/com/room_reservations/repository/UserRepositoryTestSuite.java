package com.room_reservations.repository;

import com.room_reservations.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTestSuite {

    @Autowired
    private UserRepository userRepository;


    @Test
    void testUserRepositorySave() {
        //Given
        User user = new User("test user", "test email", 100, "test password");

        //When
        userRepository.save(user);
        Long id = user.getId();

        //Then
        assertNotNull(id);

        //CleanUp
        userRepository.deleteById(id);
    }


    @Test
    void testUserRepositoryGetAllUsers() {
        //Given


        //When
        List<User> all = userRepository.findAll();

        //Then
        assertNotNull(all);

    }


    @Test
    void testUserRepositoryGetUser() {
        //Given
        User user = new User("test name", "test email", 100, "test password");
        userRepository.save(user);

        //When
        Optional<User> byId = userRepository.findById(user.getId());
        User user1 = byId.get();

        //Then
        assertNotNull(user1);
        assertEquals("test name", user1.getName());

        //CleanUp
        userRepository.deleteById(user1.getId());

    }


    @Test
    void testUserRepositoryDeleteUser() {
        //Given
        User user = new User("test name", "test email", 100, "test password");
        userRepository.save(user);

        //When
        userRepository.deleteById(user.getId());
        Optional<User> byId = userRepository.findById(user.getId());

        //Then
        assertEquals(Optional.empty(), byId);

    }


    @Test
    void testUserRepositoryUpdateUser() {
        //Given
        long id = userRepository.save(new User("test name", "test email", 100, "test password")).getId();

        //When
        Optional<User> byId = userRepository.findById(id);
        User user = byId.get();
        user.setName("new name");
        userRepository.save(user);

        //Then
        assertEquals("new name", user.getName());

        //CleanUp
        userRepository.deleteById(id);

    }

}
