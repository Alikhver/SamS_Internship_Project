package com.alikhver.model.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ValidationHelper {
    public void validateForCorrectId(Long id, String varName) {
        log.info("validateForCorrectId -> start");

        Optional.ofNullable(id)
                .filter(v -> v > 0)
                .orElseThrow(() -> {
                    var errorMessage = varName + " must not be empty. It must be positive number";
                    log.warn(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });

        log.info("validateForCorrectId -> done");
    }

    public void validateForCorrectString(String str, String varName) {
        log.info("validateForCorrectString -> start");

        Optional.ofNullable(str)
                .filter(v -> !v.isBlank())
                .orElseThrow(() -> {
                    var errorMessage = varName + " must not be empty";
                    log.warn(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });

        log.info("validationForCorrectString -> done");
    }

    public void validateForCorrectPrice(Double price, String varName) {
        log.info("validateForCorrectPrice -> start");

        Optional.ofNullable(price)
                .filter(v -> v > 0)
                .orElseThrow(() -> {
                    var errorMessage = varName + " must not be empty. It must be positive number";
                    log.warn(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });

        log.info("validateForCorrectPrice -> done");
    }
}
