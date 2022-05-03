package com.api.controllers;

import com.api.DTOS.ParkingSpotDTO;
import com.api.Services.ParkingSpotService;
import com.api.models.ParkingSpotModel;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/parking-spot")
public class ParkingSpotController {


    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        //    um ap tem uma vaga

        try {
            if (parkingSpotService.existsByApartmentAndBlock(parkingSpotDTO.getApartment(), parkingSpotDTO.getBlock())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Apartamento já cadastrado!");
            }

            var parkingSpotModel = new ParkingSpotModel();
//            BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);

            parkingSpotModel.setBlock(parkingSpotDTO.getBlock());
            parkingSpotModel.setApartment(parkingSpotDTO.getApartment());
            parkingSpotModel.setResponsibleName(parkingSpotDTO.getResponsibleName());
            parkingSpotModel.setLicensePlateCar(parkingSpotDTO.getLicensePlateCar());
            parkingSpotModel.setBrandCar(parkingSpotDTO.getBrandCar());
            parkingSpotModel.setModelCar(parkingSpotDTO.getModelCar());
            parkingSpotModel.setColorCar(parkingSpotDTO.getColorCar());
            parkingSpotModel.setParkingSpotNumber(parkingSpotDTO.getBlock() + parkingSpotDTO.getApartment());
            parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

            return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }

    }

}
