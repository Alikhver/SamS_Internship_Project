package com.alikhver.model.service.util;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.Profile;
import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.entity.User;
import com.alikhver.model.entity.Utility;
import com.alikhver.model.entity.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class ServiceValidationHelper {
    public void validateForCorrectId(Long id, String varName) {
        Optional.ofNullable(id)
                .filter(v -> v > 0)
                .orElseThrow(() -> {
                    var errorMessage = varName + " must not be empty. It must be positive number";
                    log.warn(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });
        //TODO refactor
    }

    public void validateForCorrectString(String str, String varName) {
        Optional.ofNullable(str)
                .filter(v -> !v.isBlank())
                .orElseThrow(() -> new IllegalArgumentException(
                        varName + " must not be empty."
                ));
    }

    public void validateOrganisation(Organisation organisation) {
        validateForCorrectString(organisation.getName(), "Organisation Name");
        validateForCorrectString(organisation.getDescription(), "Organisation Description");
        validateUser(organisation.getRedactor());
        if (organisation.getDateCreated() == null) organisation.setDateCreated(new Date());
    }

    public void validateProfile(Profile profile) {
        validateForCorrectString(profile.getFirstName(), "Profile First Name");
        validateForCorrectString(profile.getLastName(), "Profile Last Name");
        validateForCorrectString(profile.getEmail(), "Profile Email");
        validateForCorrectString(profile.getPhoneNumber(), "Profile phone number");
        validateUser(profile.getUser());
        if (profile.getDateCreated() == null) profile.setDateCreated(new Date());
    }

    public void validateUser(User user) {
        validateForCorrectString(user.getLogin(), "User Login");
        validateForCorrectString(user.getPassword(), "User Password");
        Objects.requireNonNull(user.getRole());
    }

    public void validateUtility(Utility utility) {
        validateForCorrectString(utility.getName(), "Utility Name");
        Optional.ofNullable(utility.getPrice()).filter(v -> v > 0).orElseThrow(() -> new IllegalArgumentException(
                "Utility Price " + " must not be empty. It must be positive number"
        ));
        validateForCorrectString(utility.getDescription(), "Utility Description");
        validateOrganisation(utility.getOrganisation());
    }

    public void validateWorker(Worker worker) {
        validateForCorrectString(worker.getFirstName(), "Worker First Name");
        validateForCorrectString(worker.getDescription(), "Worker Last Name");
        validateForCorrectString(worker.getDescription(), "Worker Description");
        validateOrganisation(worker.getOrganisation());
    }

    public void validateScheduleRecord(ScheduleRecord scheduleRecord) {
        //TODO implement
    }
}
