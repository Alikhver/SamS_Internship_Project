package com.alikhver.model.repository;

import com.alikhver.model.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
    boolean existsOrganisationByName(String name);

    void deleteAll();
}
