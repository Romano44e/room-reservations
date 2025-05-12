package com.room_reservations.scheduler;

import com.room_reservations.domain.Reservation;
import com.room_reservations.domain.ReservationStatus;
import com.room_reservations.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArchivizationSchedulerTestSuite {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ArchivizationScheduler archivizationScheduler;

    @Test
    void shouldArchiveOldReservationsOnly() {
        // given
        Reservation oldReservation = new Reservation();
        oldReservation.setEndDateTime(LocalDateTime.now().minusDays(1));
        oldReservation.setReservationStatus("ACTIVE");

        Reservation futureReservation = new Reservation();
        futureReservation.setEndDateTime(LocalDateTime.now().plusDays(1));
        futureReservation.setReservationStatus("ACTIVE");

        List<Reservation> reservations = List.of(oldReservation, futureReservation);
        when(reservationRepository.findAll()).thenReturn(reservations);

        // when
        archivizationScheduler.archivizeOldReservations();

        // then
        verify(reservationRepository).save(argThat(res ->
                res.getEndDateTime().isBefore(LocalDateTime.now()) &&
                        ReservationStatus.ARCHIVED.name().equals(res.getReservationStatus())
        ));

        verify(reservationRepository, times(1)).save(any()); // tylko jedna archiwizacja
    }
}
