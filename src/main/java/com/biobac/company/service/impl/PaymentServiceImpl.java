package com.biobac.company.service.impl;

import com.biobac.company.dto.PaymentHistoryDto;
import com.biobac.company.entity.Account;
import com.biobac.company.entity.Payment;
import com.biobac.company.entity.PaymentCategory;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.PaymentMapper;
import com.biobac.company.repository.AccountRepository;
import com.biobac.company.repository.PaymentCategoryRepository;
import com.biobac.company.repository.PaymentRepository;
import com.biobac.company.request.PaymentRequest;
import com.biobac.company.response.PaymentResponse;
import com.biobac.company.service.PaymentHistoryService;
import com.biobac.company.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final AccountRepository accountRepository;
    private final PaymentCategoryRepository paymentCategoryRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentHistoryService paymentHistoryService;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponse getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found"));
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional
    public void payment(PaymentRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found"));
        PaymentCategory category = paymentCategoryRepository.findById(request.getPaymentCategoryId())
                .orElseThrow(() -> new NotFoundException("Payment Category not found"));

        if (request.getSum() == null) {
            throw new IllegalArgumentException("Payment sum is required");
        }

        account.setBalance(account.getBalance().subtract(request.getSum()));
        accountRepository.save(account);

        PaymentHistoryDto dto = new PaymentHistoryDto();
        dto.setDate(request.getDate() == null ? LocalDateTime.now() : request.getDate());
        dto.setAccountId(account.getId());
        dto.setPaymentCategoryId(category.getId());
        dto.setIncreased(false);
        dto.setNotes(request.getNotes());
        dto.setSum(request.getSum());
        paymentHistoryService.recordHistory(dto);

        Payment payment = new Payment();
        payment.setDate(dto.getDate());
        payment.setAccount(account);
        payment.setPaymentCategory(category);
        payment.setNotes(request.getNotes());
        payment.setSum(request.getSum());
        paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void updatePayment(Long id, PaymentRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found"));

        if (request.getSum() == null) {
            throw new IllegalArgumentException("Payment sum is required");
        }

        BigDecimal oldSum = payment.getSum() == null ? BigDecimal.ZERO : payment.getSum();
        Account oldAccount = payment.getAccount();

        Account newAccount = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found"));
        PaymentCategory newCategory = paymentCategoryRepository.findById(request.getPaymentCategoryId())
                .orElseThrow(() -> new NotFoundException("Payment Category not found"));
        BigDecimal newSum = request.getSum();

        if (oldAccount != null && oldAccount.getId().equals(newAccount.getId())) {
            BigDecimal delta = newSum.subtract(oldSum);
            if (delta.compareTo(BigDecimal.ZERO) != 0) {
                newAccount.setBalance(newAccount.getBalance().subtract(delta));
                accountRepository.save(newAccount);
            }
        } else {
            if (oldAccount != null) {
                oldAccount.setBalance(oldAccount.getBalance().add(oldSum));
                accountRepository.save(oldAccount);
            }
            newAccount.setBalance(newAccount.getBalance().subtract(newSum));
            accountRepository.save(newAccount);
        }

        payment.setDate(request.getDate() != null ? request.getDate() : payment.getDate());
        payment.setAccount(newAccount);
        payment.setPaymentCategory(newCategory);
        payment.setNotes(request.getNotes());
        payment.setSum(newSum);
        paymentRepository.save(payment);
    }
}
