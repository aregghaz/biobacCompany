package com.biobac.company.mapper;

import com.biobac.company.entity.Account;
import com.biobac.company.request.AccountRequest;
import com.biobac.company.response.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "ourCompany", ignore = true)
    @Mapping(source = "bik", target = "bankInfo.bik")
    @Mapping(source = "bankAccount", target = "bankInfo.bankAccount")
    @Mapping(source = "ks", target = "bankInfo.ks")
    @Mapping(source = "bankName", target = "bankInfo.bankName")
    Account toEntity(AccountRequest request);

    @Mapping(source = "bankInfo.bik", target = "bik")
    @Mapping(source = "bankInfo.bankAccount", target = "bankAccount")
    @Mapping(source = "bankInfo.ks", target = "ks")
    @Mapping(source = "bankInfo.bankName", target = "bankName")
    @Mapping(source = "ourCompany.id", target = "ourCompanyId")
    AccountResponse toResponse(Account entity);

    @Mapping(source = "request.bik", target = "bankInfo.bik")
    @Mapping(source = "request.bankAccount", target = "bankInfo.bankAccount")
    @Mapping(source = "request.ks", target = "bankInfo.ks")
    @Mapping(source = "request.bankName", target = "bankInfo.bankName")
    void updateAccountFromRequest(AccountRequest request, @MappingTarget Account account);
}
