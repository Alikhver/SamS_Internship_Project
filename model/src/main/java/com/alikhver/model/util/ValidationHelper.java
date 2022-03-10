package com.alikhver.model.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ValidationHelper {
    public void validateForCorrectId(Long id, String varName) {
        log.info("validationHelper::validateForCorrectId -> start");

        Optional.ofNullable(id)
                .filter(v -> v > 0)
                .orElseThrow(() -> {
                    var errorMessage = varName + " must not be empty. It must be positive number";
                    log.warn(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });

        log.info("validationHelper::validateForCorrectId -> done");
    }

    public void validateForCorrectString(String str, String varName) {
        log.info("validationHelper::validateForCorrectString -> start");

        Optional.ofNullable(str)
                .filter(v -> !v.isBlank())
                .orElseThrow(() -> {
                    var errorMessage = varName + " must not be empty";
                    log.warn(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });

        log.info("validationHelper::validationForCorrectString -> done");
    }

    public void validateForCorrectPrice(Double price, String varName) {
        log.info("validationHelper::validateForCorrectPrice -> start");

        Optional.ofNullable(price)
                .filter(v -> v > 0)
                .orElseThrow(() -> {
                    var errorMessage = varName + " must not be empty. It must be positive number";
                    log.warn(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });

        log.info("validationHelper::validateForCorrectPrice -> done");
    }
}
