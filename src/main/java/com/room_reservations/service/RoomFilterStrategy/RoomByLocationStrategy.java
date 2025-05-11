package com.room_reservations.service.RoomFilterStrategy;

import com.room_reservations.domain.Room;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomByLocationStrategy implements RoomFilterStrategy<String, List<Room>> {
    @Override
    public List<Room> filter(List<Room> rooms, String location) {
        return rooms.stream()
                .filter(r -> r.getLocation().equals(location))
                .toList();
    }
}
