package com.biobac.company.mapper;

import com.biobac.company.entity.Branch;
import com.biobac.company.request.BranchRequest;
import com.biobac.company.request.BranchUpdateRequest;
import com.biobac.company.response.BranchResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "address.localAddress", source = "localAddress")
    Branch toBranchEntity(BranchRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "address.localAddress", source = "localAddress")
    Branch toBranchEntityDefault(BranchUpdateRequest request);

    @Mapping(source = "address.localAddress", target = "localAddress")
    BranchResponse toBranchResponse(Branch branch);

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "address.localAddress", source = "localAddress")
    Branch updateBranch(BranchUpdateRequest request, @MappingTarget Branch branches);
}