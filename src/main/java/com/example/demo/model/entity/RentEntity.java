package com.example.demo.model.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rent")
public class RentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "publicId")
    private String publicId;

    @OneToOne(targetEntity = BikeEntity.class)
    @JoinColumn(name = "bike")
    private BikeEntity bikeEntity;
}
