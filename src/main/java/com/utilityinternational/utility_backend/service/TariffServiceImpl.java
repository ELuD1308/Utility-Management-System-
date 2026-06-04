package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.entity.Tariff;
import com.utilityinternational.utility_backend.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;

    @Override
    public Tariff createTariff(Tariff tariff) {
        return tariffRepository.save(tariff);
    }

    @Override
    public Tariff getTariffById(Long id) {
        return tariffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tariff not found"));
    }

    @Override
    public List<Tariff> getAllTariffs() {
        return tariffRepository.findAll();
    }

    @Override
    public Tariff updateTariff(Long id, Tariff tariff) {

        Tariff existing = getTariffById(id);

        existing.setTariffName(tariff.getTariffName());
        existing.setUtilityType(tariff.getUtilityType());
        existing.setRatePerUnit(tariff.getRatePerUnit());

        return tariffRepository.save(existing);
    }

    @Override
    public void deleteTariff(Long id) {
        tariffRepository.deleteById(id);
    }
}
