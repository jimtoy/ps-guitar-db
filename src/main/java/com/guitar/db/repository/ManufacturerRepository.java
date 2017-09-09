package com.guitar.db.repository;

import com.guitar.db.model.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ManufacturerRepository {


    @Autowired
    private ManufacturerJpaRepository manufacturerJpaRepository;

    /**
     * Custom finder
     */
    public List<Manufacturer> getManufacturersFoundedBeforeDate(Date date) {

        return manufacturerJpaRepository.findByFoundedDateBefore(date);
    }

    /**
     * Custom finder
     */
    public Manufacturer getManufacturerByName(String name) {
        return manufacturerJpaRepository.getManufacturerByName(name);
    }

    /**
     * Native Query finder
     */
    public List<Manufacturer> getManufacturersThatSellModelsOfType(String modelType) {
        return manufacturerJpaRepository.getAllThatSellAcoustics(modelType);

    }
}
