package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.entity.Dispute;
import com.utilityinternational.utility_backend.repository.DisputeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisputeServiceImpl implements DisputeService {

    private final DisputeRepository disputeRepository;

    @Override
    public Dispute createDispute(Dispute dispute) {
        return disputeRepository.save(dispute);
    }

    @Override
    public Dispute getDisputeById(Long id) {
        return disputeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispute not found"));
    }

    @Override
    public List<Dispute> getAllDisputes() {
        return disputeRepository.findAll();
    }

    @Override
    public Dispute updateDispute(Long id, Dispute dispute) {

        Dispute existing = getDisputeById(id);

        existing.setDisputeReason(dispute.getDisputeReason());
        existing.setStatus(dispute.getStatus());
        existing.setBill(dispute.getBill());

        return disputeRepository.save(existing);
    }

    @Override
    public void deleteDispute(Long id) {
        disputeRepository.deleteById(id);
    }
}
