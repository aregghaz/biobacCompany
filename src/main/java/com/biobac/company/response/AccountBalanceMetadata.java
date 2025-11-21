package com.biobac.company.response;

import com.biobac.company.dto.PaginationMetadata;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceMetadata {
    private PaginationMetadata pagination;
    private BigDecimal sum;
}
