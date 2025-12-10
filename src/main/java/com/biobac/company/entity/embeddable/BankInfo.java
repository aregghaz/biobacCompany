package com.biobac.company.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Setter
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class BankInfo {
    private String bik;
    private String ks;
    private String bankName;
    private String bankAccount;
}
