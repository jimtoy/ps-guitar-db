package com.guitar.db;

import com.guitar.db.model.Model;
import com.guitar.db.repository.ModelJpaRepository;
import com.guitar.db.repository.ModelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(locations = {"classpath:com/guitar/db/applicationTests-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ModelPersistenceTests {
    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ModelJpaRepository modelJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testSaveAndGetAndDelete() throws Exception {
        Model m = new Model();
        m.setFrets(10);
        m.setName("Test Model");
        m.setPrice(BigDecimal.valueOf(55L));
        m.setWoodType("Maple");
        m.setYearFirstMade(new Date());
        m = modelJpaRepository.saveAndFlush(m);

        // clear the persistence context so we don't return the previously cached location object
        // this is a test only thing and normally doesn't need to be done in prod code
        entityManager.clear();

        Model otherModel = modelJpaRepository.findOne(m.getId());
        assertEquals("Test Model", otherModel.getName());
        assertEquals(10, otherModel.getFrets());

        //delete BC location now
        modelJpaRepository.delete(otherModel);

        modelJpaRepository.customMethod();
    }

    @Test
    public void testGetModelsInPriceRange() throws Exception {
        List<Model> mods = modelJpaRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal.valueOf(1000L), BigDecimal.valueOf(2000L));
        assertEquals(4, mods.size());
    }

    @Test
    public void testGetModelsByPriceRangeAndWoodType() throws Exception {
        Page<Model> mods = modelRepository.getModelsByPriceRangeAndWoodType(BigDecimal.valueOf(1000L), BigDecimal.valueOf(2000L), "Maple");

        assertEquals(2, mods.getSize());

        mods.getContent().forEach((model -> {
            System.out.println(model.getName());
        }));




    }

    @Test
    public void testGetModelsByType() throws Exception {
        List<Model> mods = modelJpaRepository.findAllModelsByType("Electric");
        assertEquals(4, mods.size());
    }

    @Test
    public void testGetModelsByTypes() throws Exception {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        List<String> types = new ArrayList<>();
        types.add("Electric");
        types.add("Acoustic");
        List<Model> mods = modelJpaRepository.findByModelTypeNameIn(types);
        assertEquals(4, mods.size());

        mods.forEach((model -> {

            assertTrue(model.getModelType().getName().equals("Electric") || model.getModelType().getName().equals("Acoustic"));

        }));
    }
}
