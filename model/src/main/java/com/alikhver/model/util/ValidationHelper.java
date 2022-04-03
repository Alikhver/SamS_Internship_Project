package com.alikhver.model.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ValidationHelper {
    public void validateForCorrectId(Long id, String varName) {
        Optional.of(id)
                .filter(v -> v > 0)
                .orElseThrow(() -> {
                    var errorMessage = varName + " must not be empty. It must be positive number";
                    log.warn(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });

    }

    public void validateForCorrectString(String str, String varName) {
        Optional.of(str)
                .filter(v -> !v.isBlank())
                .orElseThrow(() -> {
                    var errorMessage = varName + " must not be empty";
                    log.warn(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });
    }

    public void validateForCorrectPrice(Double price, String varName) {
        Optional.of(price)
                .filter(v -> v > 0)
                .orElseThrow(() -> {
                    var errorMessage = varName + " must not be empty. It must be positive number";
                    log.warn(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });

    }
}
