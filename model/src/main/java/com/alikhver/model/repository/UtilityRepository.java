package com.alikhver.model.repository;

import com.alikhver.model.entity.Utility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilityRepository extends JpaRepository<Utility, Long> {
    boolean existsByNameAndDescriptionAndPrice(String name, String description, Double price);

    Page<Utility> findAllByOrganisationId(Long organisationId, Pageable pageable);

    boolean existsByNameAndDescriptionAndPriceAndOrganisationId(String name, String description, Double price, Long organisationId);

    boolean existsByIdAndWorkersId(Long id, Long workerId);
}
