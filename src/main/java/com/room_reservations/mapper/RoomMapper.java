package com.room_reservations.mapper;

import com.room_reservations.domain.Room;
import com.room_reservations.domain.RoomDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomMapper {

    public Room mapToRoom(RoomDto roomDto) {
        return new Room(roomDto.getId(), roomDto.getName(), roomDto.getCapacity(), roomDto.getLocation(), roomDto.getPrice(), roomDto.getCipher());
    }

    public RoomDto mapToRoomDto(Room room) {
        return new RoomDto(room.getId(), room.getName(), room.getCapacity(), room.getLocation(), room.getPrice(), room.getCipher());
    }

    public List<RoomDto> mapToRoomDtoList(List<Room> rooms) {
        List<RoomDto> roomDtoList = rooms.stream()
                .map(room -> mapToRoomDto(room))
                .toList();
        return roomDtoList;
    }

}
