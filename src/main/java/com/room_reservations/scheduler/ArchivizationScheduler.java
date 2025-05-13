package com.room_reservations.scheduler;

import com.room_reservations.domain.Reservation;
import com.room_reservations.domain.ReservationStatus;
import com.room_reservations.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ArchivizationScheduler {

    private final ReservationRepository reservationRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void archivizeOldReservations() {
        List<Reservation> allReservations = reservationRepository.findAll();
        for (Reservation r : allReservations) {
            if (r.getEndDateTime().isBefore(LocalDateTime.now())) {
                r.setReservationStatus(String.valueOf(ReservationStatus.ARCHIVED));
                reservationRepository.save(r);
            }
        }
    }
}
