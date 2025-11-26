package com.biobac.company.mapper;

import com.biobac.company.entity.Account;
import com.biobac.company.request.AccountRequest;
import com.biobac.company.response.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponse toResponse(Account entity);

    void updateAccountFromRequest(AccountRequest request, @MappingTarget Account account);

    Account toEntity(AccountRequest request);
}
