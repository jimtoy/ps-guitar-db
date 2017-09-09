package com.guitar.db.repository;

import com.guitar.db.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class ModelRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelJpaRepository modelJpaRepository;


    /**
     * Custom finder
     */
    public List<Model> getModelsByPriceRangeAndWoodType(BigDecimal lowest, BigDecimal highest, String wood) {
        return modelJpaRepository.queryByPriceRangeAndWoodType(lowest, highest, "%" + wood + "%");
    }

}
