package com.alikhver.model.repository;

import com.alikhver.model.entity.Utility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilityRepository extends JpaRepository<Utility, Long> {
    boolean existsByNameAndDescriptionAndPrice(String name, String description, Double price);
    List<Utility> findAllByOrganisationId(Long organisationId);
    boolean existsByNameAndDescriptionAndPriceAndOrganisationId(String name, String description, Double price, Long organisationId);
}
