package com.guitar.db.repository;

import com.guitar.db.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class ModelRepository {

    @Autowired
    private ModelJpaRepository modelJpaRepository;


    /**
     * Custom finder
     */
    public Page<Model> getModelsByPriceRangeAndWoodType(BigDecimal lowest, BigDecimal highest, String wood) {
        Pageable page = new PageRequest(0, 2);
        return modelJpaRepository.queryByPriceRangeAndWoodType(lowest, highest, "%" + wood + "%", page);
    }

}
