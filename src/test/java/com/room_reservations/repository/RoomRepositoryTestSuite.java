package com.room_reservations.repository;

import com.room_reservations.domain.Room;
import com.room_reservations.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RoomRepositoryTestSuite {

    @Autowired
    private RoomRepository roomRepository;


    @Test
    void testRoomRepositorySave() {
        //Given
        Room room = new Room("test name", 1000, "test location", new BigDecimal(500.00), "test cipher");

        //When
        roomRepository.save(room);
        Long id = room.getId();

        //Then
        assertNotNull(id);

        //CleanUp
        roomRepository.deleteById(id);
    }


    @Test
    void testRoomRepositoryGetAllRooms() {
        //Given


        //When
        List<Room> all = roomRepository.findAll();

        //Then
        assertNotNull(all);

    }


    @Test
    void testRoomRepositoryGetRoom() {
        //Given
        Room room = new Room("test name", 1000, "test location", new BigDecimal(500.00), "test cipher");
        roomRepository.save(room);

        //When
        Optional<Room> byId = roomRepository.findById(room.getId());
        Room room1 = byId.get();

        //Then
        assertNotNull(room1);
        assertEquals("test name", room1.getName());

        //CleanUp
        roomRepository.deleteById(room1.getId());

    }


    @Test
    void testRoomRepositoryDeleteRoom() {
        //Given
        Room room = new Room("test name", 1000, "test location", new BigDecimal(500.00), "test cipher");
        roomRepository.save(room);

        //When
        roomRepository.deleteById(room.getId());
        Optional<Room> byId = roomRepository.findById(room.getId());

        //Then
        assertEquals(Optional.empty(), byId);

    }


    @Test
    void testRoomRepositoryUpdateRoom() {
        //Given
        long id = roomRepository.save(new Room("test name", 1000, "test location", new BigDecimal(500.00), "test cipher")).getId();

        //When
        Optional<Room> byId = roomRepository.findById(id);
        Room room = byId.get();
        room.setName("new name");
        roomRepository.save(room);

        //Then
        assertEquals("new name", room.getName());

        //CleanUp
        roomRepository.deleteById(id);

    }

}
