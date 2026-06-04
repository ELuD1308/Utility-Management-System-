package com.utilityinternational.utility_backend.controller;

import com.utilityinternational.utility_backend.entity.Tariff;
import com.utilityinternational.utility_backend.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
@RequiredArgsConstructor
public class TariffController {

    private final TariffService tariffService;

    @PostMapping
    public Tariff create(@RequestBody Tariff tariff) {
        return tariffService.createTariff(tariff);
    }

    @GetMapping("/{id}")
    public Tariff getById(@PathVariable Long id) {
        return tariffService.getTariffById(id);
    }

    @GetMapping
    public List<Tariff> getAll() {
        return tariffService.getAllTariffs();
    }

    @PutMapping("/{id}")
    public Tariff update(@PathVariable Long id,
                         @RequestBody Tariff tariff) {
        return tariffService.updateTariff(id, tariff);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tariffService.deleteTariff(id);
    }
}
