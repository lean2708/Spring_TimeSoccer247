package com.timesoccer247.Spring_TimeSoccer247.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.timesoccer247.Spring_TimeSoccer247.constants.BookingStatus;
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
@Table(name = "tbl_booking")
public class Booking extends BaseEntity {

    LocalDateTime startTime;
    LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "field_id")
    Field field;

    @OneToOne(mappedBy = "booking")
    @JsonIgnore
    Payment payment;
}
