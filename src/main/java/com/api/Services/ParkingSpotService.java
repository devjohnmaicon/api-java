package com.api.Services;

import com.api.models.ParkingSpotModel;
import com.api.repositories.ParkingSpotRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

@Service
public class ParkingSpotService {

   final ParkingSpotRepository parkingSpotRepository;

   public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
      this.parkingSpotRepository = parkingSpotRepository;
   }

   @Transactional
   public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {

      if (existsByApartmentAndBlock(parkingSpotModel.getApartment(), parkingSpotModel.getBlock())) {
         throw new RuntimeException("'Error': Já cadastrado!");
      }

      return parkingSpotRepository.save(parkingSpotModel);
   }

   public boolean existsByApartmentAndBlock(String apartment, String block) {
      return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
   }

   public Page<ParkingSpotModel> findAll(Pageable pageable) {
      return parkingSpotRepository.findAll(pageable);
   }

   public Optional<ParkingSpotModel> findById(UUID id) {
      return parkingSpotRepository.findById(id);
   }

   @Transactional
   public void delete(ParkingSpotModel parkingSpotModel) {
      parkingSpotRepository.delete(parkingSpotModel);
   }

}
