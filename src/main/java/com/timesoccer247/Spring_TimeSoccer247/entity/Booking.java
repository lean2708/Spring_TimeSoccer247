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
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "tbl_booking")
public class Booking extends BaseEntity {

    @Column(name = "start_time")
    LocalDateTime startTime;
    @Column(name = "end_time")
    LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    BookingStatus status = BookingStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "field_id")
    Field field;

    @ManyToMany
    @JoinTable(
            name = "tbl_booking_ball",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "ball_id")
    )
    Set<Ball> balls;

    @OneToOne(mappedBy = "booking")
    @JsonIgnore
    Payment payment;


}
