package com.hotel.reservation.entity;

import com.hotel.reservation.entity.enums.ReservationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "reservation_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Reservation extends BaseClass{

    @Column(nullable = false, unique = true)
    private String reservationCode;

    @Column(nullable = false)
    private LocalDateTime checkIn;

    @Column(nullable = false)
    private LocalDateTime CheckOut;

    @Column(nullable = false)
    @Positive
    private BigDecimal totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}
