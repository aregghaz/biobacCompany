package com.biobac.company.mapper;

import com.biobac.company.entity.Branch;
import com.biobac.company.request.BranchRequest;
import com.biobac.company.response.BranchResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "address.localAddress", source = "localAddress")
    Branch toBranchEntity(BranchRequest request);

    @Mapping(source = "address.localAddress", target = "localAddress")
    BranchResponse toBranchResponse(Branch branch);
}