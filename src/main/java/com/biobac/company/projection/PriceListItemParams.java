package com.biobac.company.projection;

import java.time.LocalDateTime;

public interface PriceListItemParams {
    String getName();

    String getPrice();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
