package com.example.stations.model.entity;

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
@Table(name = "station")
public class StationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "publicId")
    private String publicId;

    @Column(name = "name")
    private String name;

    @Column (name = "number")
    private String number;

    @JsonManagedReference
    @OneToMany(targetEntity = StandEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "station")
    private Set<StandEntity> stands;
}
