package com.example.demo.model.entity;

import lombok.*;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "station", insertable = false, updatable = false)
    private StationEntity station;
}
