package com.example.stations.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bike")
public class BikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "publicId")
    private String publicId;

    @Column(name = "number")
    private String number;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "stand", insertable = false, updatable = false)
    private StandEntity stand;
}
