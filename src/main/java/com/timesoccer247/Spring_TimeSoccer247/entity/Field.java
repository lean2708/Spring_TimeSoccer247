package com.timesoccer247.Spring_TimeSoccer247.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "tbl_field")
public class Field extends BaseEntity {

    String name;
    double price;

    @OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Booking> bookings;
}
