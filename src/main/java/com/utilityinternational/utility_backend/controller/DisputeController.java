package com.utilityinternational.utility_backend.controller;

import com.utilityinternational.utility_backend.entity.Dispute;
import com.utilityinternational.utility_backend.service.DisputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disputes")
@RequiredArgsConstructor
public class DisputeController {

    private final DisputeService disputeService;

    @PostMapping
    public Dispute create(@RequestBody Dispute dispute) {
        return disputeService.createDispute(dispute);
    }

    @GetMapping("/{id}")
    public Dispute getById(@PathVariable Long id) {
        return disputeService.getDisputeById(id);
    }

    @GetMapping
    public List<Dispute> getAll() {
        return disputeService.getAllDisputes();
    }

    @PutMapping("/{id}")
    public Dispute update(@PathVariable Long id,
                          @RequestBody Dispute dispute) {
        return disputeService.updateDispute(id, dispute);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        disputeService.deleteDispute(id);
    }
}
