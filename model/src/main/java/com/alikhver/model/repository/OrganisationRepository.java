package com.alikhver.model.repository;

import com.alikhver.model.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
    boolean existsOrganisationByName(String name);

    void deleteAll();

    Optional<Organisation> getOrganisationByRedactorLogin(String login);
}
