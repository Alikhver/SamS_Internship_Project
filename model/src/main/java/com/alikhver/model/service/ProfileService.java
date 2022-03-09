package com.alikhver.model.service;

import com.alikhver.model.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProfileService {

    void save(Profile profile);

    boolean existsById(Long id);

    Optional<Profile> get(Long id);

    Page<Profile> getAll(Pageable pageable);
}
