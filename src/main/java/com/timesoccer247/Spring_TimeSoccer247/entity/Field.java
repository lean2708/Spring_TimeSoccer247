package com.timesoccer247.Spring_TimeSoccer247.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
@Table(name = "tbl_field")
public class Field {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    long id;
    String name;
    double price;
}
