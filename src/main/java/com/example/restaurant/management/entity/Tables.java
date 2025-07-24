package com.example.restaurant.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tables")
public class Tables {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "table_type_id")
    private TableType tableType;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Integer maxPerson;
    private String note;

    @PrePersist
    private void prePersist(){
        if(status==null || status.isBlank()) setStatus("Trống");
        if(maxPerson==null) setMaxPerson(4);
        if(note==null || note.isBlank()) setNote("");
    }
}
