package com.timesoccer247.Spring_TimeSoccer247.entity;

import com.timesoccer247.Spring_TimeSoccer247.constants.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "tbl_payment")
public class Payment extends BaseEntity {

    @Enumerated(EnumType.STRING)
    PaymentStatus status;
    double amount;
    LocalDateTime paymentTime;

    @OneToOne
    @JoinColumn(name = "booking_id")
    Booking booking;
}
