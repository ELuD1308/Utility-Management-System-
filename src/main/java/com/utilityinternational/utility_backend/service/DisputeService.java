package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.entity.Dispute;

import java.util.List;

public interface DisputeService {

    Dispute createDispute(Dispute dispute);

    Dispute getDisputeById(Long id);

    List<Dispute> getAllDisputes();

    Dispute updateDispute(Long id, Dispute dispute);

    void deleteDispute(Long id);
}
