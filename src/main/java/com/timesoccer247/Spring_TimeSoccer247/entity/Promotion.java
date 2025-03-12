package com.timesoccer247.Spring_TimeSoccer247.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "tbl_promotion")
public class Promotion extends BaseEntity {

    double discount;
    LocalDate validUntil;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
