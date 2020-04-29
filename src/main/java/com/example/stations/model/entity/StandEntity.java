package com.example.stations.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stand")
public class StandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "publicId")
    private String publicId;

    @Column(name = "available")
    private Boolean available;

    @Column (name = "number")
    private String number;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "station", insertable = false, updatable = false)
    private StationEntity station;

    @JsonManagedReference
    @OneToMany(targetEntity = BikeEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "stand")
    private Set<BikeEntity> bikes;
}
