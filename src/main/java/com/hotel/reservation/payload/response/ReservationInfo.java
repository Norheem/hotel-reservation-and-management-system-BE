package com.hotel.reservation.payload.response;


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
public class ReservationInfo {

    private String reservationCode;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private BigDecimal totalPrice;

    private ReservationStatus reservationStatus;

    private Long customerId;

    private String customerName;

    private Long roomId;

    private String roomNumber;
}
