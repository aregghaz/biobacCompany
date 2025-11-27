package com.biobac.company.service;

import com.biobac.company.request.PaymentRequest;
import com.biobac.company.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse getById(Long id);

    void payment(PaymentRequest request);

    void updatePayment(Long id, PaymentRequest request);
}
