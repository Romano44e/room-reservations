package com.room_reservations.service.RoomFilterStrategy;

import com.room_reservations.domain.Room;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RoomByPriceStrategy implements RoomFilterStrategy<BigDecimal, List<Room>> {
    @Override
    public List<Room> filter(List<Room> rooms, BigDecimal price) {
        return rooms.stream()
                .filter(r -> r.getPrice().toBigInteger().doubleValue() >= price.doubleValue())
                .toList();
    }
}
