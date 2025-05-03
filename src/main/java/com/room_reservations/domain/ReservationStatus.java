package com.room_reservations.domain;

public enum ReservationStatus {
    NEW,         // utworzona, ale jeszcze nie potwierdzona
    CONFIRMED,   // zatwierdzona i aktywna
    CANCELLED,   // anulowana przez użytkownika lub system
    REJECTED,    // odrzucona (np. kolizja lub błąd)
    ARCHIVED     // zakończona, przeniesiona do archiwum
}
