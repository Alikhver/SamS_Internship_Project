package com.alikhver.model.service;

import com.alikhver.model.entity.Profile;

public interface ProfileService {

    void createProfile(Profile profile);

    boolean profileExistsById(Long id);

    Profile getProfile(Long id);
}
