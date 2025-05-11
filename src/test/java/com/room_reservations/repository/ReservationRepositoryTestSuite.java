package com.room_reservations.repository;

import com.room_reservations.domain.Reservation;
import com.room_reservations.domain.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ReservationRepositoryTestSuite {

    @Autowired
    private ReservationRepository reservationRepository;


    @Test
    void testReservationRepositorySave() {
        //Given
        Reservation reservation = new Reservation(1L, 1L, LocalDateTime.now(), LocalDateTime.of(2025, 5, 20, 18, 30), "test reservation status", "test payment status", "test currency", new BigDecimal(500.00), "test code");

        //When
        reservationRepository.save(reservation);
        Long id = reservation.getId();

        //Then
        assertNotNull(id);

        //CleanUp
        reservationRepository.deleteById(id);
    }


    @Test
    void testReservationRepositoryGetAllReservations() {
        //Given


        //When
        List<Reservation> all = reservationRepository.findAll();

        //Then
        assertNotNull(all);

    }


    @Test
    void testReservationRepositoryGetReservation() {
        //Given
        Reservation reservation = new Reservation(1L, 1L, LocalDateTime.now(), LocalDateTime.of(2025, 5, 20, 18, 30), "test reservation status", "test payment status", "test currency", new BigDecimal(500.00), "test code");
        reservationRepository.save(reservation);

        //When
        Optional<Reservation> byId = reservationRepository.findById(reservation.getId());
        Reservation reservation1 = byId.get();

        //Then
        assertNotNull(reservation1);
        assertEquals("test reservation status", reservation1.getReservationStatus());

        //CleanUp
        reservationRepository.deleteById(reservation1.getId());

    }


    @Test
    void testReservationRepositoryDeleteReservation() {
        //Given
        Reservation reservation = new Reservation(1L, 1L, LocalDateTime.now(), LocalDateTime.of(2025, 5, 20, 18, 30), "test reservation status", "test payment status", "test currency", new BigDecimal(500.00), "test code");
        reservationRepository.save(reservation);

        //When
        reservationRepository.deleteById(reservation.getId());
        Optional<Reservation> byId = reservationRepository.findById(reservation.getId());

        //Then
        assertEquals(Optional.empty(), byId);

    }


    @Test
    void testReservationRepositoryUpdateReservation() {
        //Given
        long id = reservationRepository.save(new Reservation(1L, 1L, LocalDateTime.now(), LocalDateTime.of(2025, 5, 20, 18, 30), "test reservation status", "test payment status", "test currency", new BigDecimal(500.00), "test code")).getId();

        //When
        Optional<Reservation> byId = reservationRepository.findById(id);
        Reservation reservation = byId.get();
        reservation.setReservationStatus("new Reservation status");
        reservationRepository.save(reservation);

        //Then
        assertEquals("new Reservation status", reservation.getReservationStatus());

        //CleanUp
        reservationRepository.deleteById(id);

    }

}
