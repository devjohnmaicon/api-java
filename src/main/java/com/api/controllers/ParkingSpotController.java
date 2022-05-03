package com.api.controllers;

import com.api.DTOS.ParkingSpotDTO;
import com.api.Services.ParkingSpotService;
import com.api.models.ParkingSpotModel;
import org.springframework.beans.BeanUtils;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        try {

            if (parkingSpotService.existsByApartmentAndBlock(parkingSpotDTO.getApartment(),
                    parkingSpotDTO.getBlock())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Apartamento já cadastrado!");
            }

            var parkingSpotModel = new ParkingSpotModel();
            BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);

            parkingSpotModel.setParkingSpotNumber(parkingSpotDTO.getBlock() + parkingSpotDTO.getApartment());
            parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

            return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }

    }

    @GetMapping
    // pagi(10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

    public ResponseEntity<Object> getAllParkingSpots() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getParkingSpotById(@PathVariable(value = "id") UUID id) {

        Optional<ParkingSpotModel> parkingSpotOptional = parkingSpotService.findById(id);

        if (!parkingSpotOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não encontrado!");

        }

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotOptional);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id) {

        Optional<ParkingSpotModel> parkingSpotOptional = parkingSpotService.findById(id);

        if (!parkingSpotOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não encontrado!");

        }

        parkingSpotService.delete(parkingSpotOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso!");

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {

        Optional<ParkingSpotModel> parkingSpotOptional = parkingSpotService.findById(id);

        if (!parkingSpotOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não encontrado!");

        }

        var parkingSpotUpdate = new ParkingSpotModel();

        BeanUtils.copyProperties(parkingSpotDTO, parkingSpotUpdate);
        parkingSpotUpdate.setId(parkingSpotOptional.get().getId());
        parkingSpotUpdate.setParkingSpotNumber(parkingSpotDTO.getBlock() + parkingSpotDTO.getApartment());
        parkingSpotUpdate.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotUpdate));

    }

}
