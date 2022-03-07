package com.alikhver.model.service;

import com.alikhver.model.entity.Utility;
import com.alikhver.model.repository.UtilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UtilityServiceImpl implements UtilityService {
    private final UtilityRepository repository;

    @Override
    public void save(Utility utility) {
        Objects.requireNonNull(utility.getName());
        Objects.requireNonNull(utility.getPrice());
        Objects.requireNonNull(utility.getDescription());
        Objects.requireNonNull(utility.getOrganisation());

        repository.save(utility);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean utilityExists(Utility utility) {
        return repository.existsByNameAndDescriptionAndPriceAndOrganisationId(
                utility.getName(),
                utility.getDescription(),
                utility.getPrice(),
                utility.getOrganisation().getId()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public boolean utilityExists(Long id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Utility> getUtility(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Utility> getAllUtilitiesOfOrganisation(Long id) {
        return repository.findAllByOrganisationId(id);
    }

    @Override
    public void deleteUtility(Long id) {
        repository.deleteById(id);
    }
}
