package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.entity.Tariff;

import java.util.List;

public interface TariffService {

    Tariff createTariff(Tariff tariff);

    Tariff getTariffById(Long id);

    List<Tariff> getAllTariffs();

    Tariff updateTariff(Long id, Tariff tariff);

    void deleteTariff(Long id);
}
