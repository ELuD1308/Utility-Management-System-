package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.dto.request.DisputeRequest;
import com.utilityinternational.utility_backend.dto.request.DisputeStatusUpdateRequest;
import com.utilityinternational.utility_backend.dto.response.DisputeResponse;

import java.util.List;

public interface DisputeService {

    DisputeResponse openDispute(Long customerId, DisputeRequest request);

    List<DisputeResponse> getDisputesByCustomer(Long customerId);

    DisputeResponse getDisputeById(Long customerId, Long disputeId);

    DisputeResponse updateDisputeStatus(Long disputeId, DisputeStatusUpdateRequest request);

    List<DisputeResponse> getAllDisputes();
}
