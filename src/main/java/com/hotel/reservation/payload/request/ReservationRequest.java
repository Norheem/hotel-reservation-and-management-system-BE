package com.hotel.reservation.payload.request;


import com.hotel.reservation.entity.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequest {

    private Long customerId;

    private String customerName;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private BigDecimal totalPrice;

    private Long roomId;

    private ReservationStatus reservationStatus;
}
