package com.room_reservations.service.RoomFilterStrategy;

import com.room_reservations.domain.Room;

import java.util.List;

public interface RoomFilterStrategy<T,R> {
    R filter(List<Room> rooms, T input);
}
