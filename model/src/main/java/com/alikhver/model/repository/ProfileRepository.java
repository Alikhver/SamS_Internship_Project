package com.alikhver.model.repository;

import com.alikhver.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    boolean existsProfileByUserId(Long userId);

    boolean existsProfileByEmail(String email);

    boolean existsProfileByPhoneNumber(String phoneNumber);

    Optional<Profile> getProfileByUserId(Long userId);
}
