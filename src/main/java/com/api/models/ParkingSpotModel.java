package com.api.models;

import javax.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "parking_spot_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpotModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 130)
    private String responsibleName;

    @Column(nullable = false, length = 30)
    private String apartment;

    @Column(nullable = false, length = 30)
    private String block;

    @Column(nullable = false, unique = true)
    private String parkingSpotNumber;

    @Column(nullable = false, length = 70)
    private String brandCar;

    @Column(nullable = false, length = 7)
    private String licensePlateCar;

    @Column(nullable = false, length = 70)
    private String modelCar;

    @Column(nullable = false, length = 70)
    private String colorCar;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

}
