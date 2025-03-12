package com.timesoccer247.Spring_TimeSoccer247.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {
    @Id
    private String id;

    private String fileName;
    private String url;
    private String type;
}
