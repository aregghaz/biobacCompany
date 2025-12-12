package com.biobac.company.controller;

import com.biobac.company.service.BalanceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company/balance-history")
@RequiredArgsConstructor
public class BalanceHistoryController {
    private final BalanceHistoryService balanceHistoryService;
}
