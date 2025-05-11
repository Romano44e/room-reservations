package com.room_reservations.service.RoomFilterStrategy;

import com.room_reservations.domain.Room;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomByCapacityStrategy implements RoomFilterStrategy<Integer, List<Room>> {
    @Override
    public List<Room> filter(List<Room> rooms, Integer capacity) {
        return rooms.stream()
                .filter(r -> r.getCapacity() >= capacity)
                .toList();
    }
}
