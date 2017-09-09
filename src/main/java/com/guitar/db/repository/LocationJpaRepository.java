package com.guitar.db.repository;

import com.guitar.db.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationJpaRepository extends JpaRepository<Location, Long> {

    List<Location> findByStateStartsWith(String stateName);

    List<Location> findByStateOrCountry(String stateName, String countryName);

    List<Location> findByStateAndCountry(String stateName, String countryName);

}
