package com.example.demo.model.entity;

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

    @ManyToOne
    @JoinColumn(name = "station", insertable = false, updatable = false)
    private StationEntity station;
}
