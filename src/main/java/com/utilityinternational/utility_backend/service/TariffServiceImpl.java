package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.dto.request.TariffChangeRequest;
import com.utilityinternational.utility_backend.dto.response.TariffResponse;
import com.utilityinternational.utility_backend.entity.Customer;
import com.utilityinternational.utility_backend.entity.Tariff;
import com.utilityinternational.utility_backend.enums.UtilityType;
import com.utilityinternational.utility_backend.exception.ResourceNotFoundException;
import com.utilityinternational.utility_backend.exception.TariffNotFoundException;
import com.utilityinternational.utility_backend.repository.CustomerRepository;
import com.utilityinternational.utility_backend.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;
    private final CustomerRepository customerRepository;
    private final NotificationService notificationService;

    // ── Browse all tariffs ─────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<TariffResponse> getAllTariffs() {
        return tariffRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ── Browse by utility type ─────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<TariffResponse> getTariffsByUtilityType(UtilityType utilityType) {
        return tariffRepository.findByUtilityType(utilityType)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ── Change customer tariff ─────────────────────────────────────────────────
    // NOTE: Customer entity currently has no currentTariff field.
    // Ask your DB teammate to add:
    //   @ManyToOne @JoinColumn(name = "current_tariff_id")
    //   private Tariff currentTariff;
    //
    // Then uncomment the two lines marked UNCOMMENT WHEN READY below.
    @Override
    @Transactional
    public TariffResponse changeCustomerTariff(Long customerId, TariffChangeRequest request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer", "id", customerId));

        Tariff targetTariff = tariffRepository.findById(request.getTargetTariffId())
                .orElseThrow(() ->
                        new TariffNotFoundException(request.getTargetTariffId()));

        if (customer.isAccountLocked()) {
            throw new IllegalStateException(
                    "Tariff cannot be changed for a locked account. Customer ID: " + customerId);
        }

        // UNCOMMENT WHEN READY — after DB teammate adds currentTariff field:
        // customer.setCurrentTariff(targetTariff);
        // customerRepository.save(customer);

        log.info("Tariff changed to [{}] for customer [{}]",
                targetTariff.getTariffName(), customerId);

        notificationService.sendNotification(
                customer,
                "Your tariff has been updated to: " + targetTariff.getTariffName()
                + " at ₦" + targetTariff.getRatePerUnit() + " per unit.",
                "TARIFF_CHANGED"
        );

        return mapToResponse(targetTariff);
    }

    // ── Entity → DTO ──────────────────────────────────────────────────────────
    private TariffResponse mapToResponse(Tariff tariff) {
        return TariffResponse.builder()
                .tariffId(tariff.getId())
                .tariffName(tariff.getTariffName())
                .utilityType(tariff.getUtilityType())
                .ratePerUnit(tariff.getRatePerUnit())
                .build();
    }
}
