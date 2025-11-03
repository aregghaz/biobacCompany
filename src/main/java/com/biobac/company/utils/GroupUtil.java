package com.biobac.company.utils;

import com.biobac.company.client.UserClient;
import com.biobac.company.entity.CompanyGroup;
import com.biobac.company.exception.ExternalServiceException;
import com.biobac.company.repository.CompanyGroupRepository;
import com.biobac.company.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class GroupUtil {

    private final UserClient userClient;
    private final SecurityUtil securityUtil;
    private final CompanyGroupRepository companyGroupRepository;

    public List<Long> getAccessibleCompanyGroupIds() {
        List<Long> groupIds;
        if (accessToAllGroups()) {
            groupIds = companyGroupRepository.findAll().stream().map(CompanyGroup::getId).toList();
        } else {
            groupIds = getAccessibleGroupIds(userClient::getCompanyGroupIds, "company");
        }
        return groupIds;
    }

    private boolean accessToAllGroups() {
        return securityUtil.hasPermission("ALL_GROUP_ACCESS");
    }

    private List<Long> getAccessibleGroupIds(Function<Long, ApiResponse<List<Long>>> fetcher, String type) {
        Long userId = securityUtil.getCurrentUserId();
        if (userId == null) {
            throw new ExternalServiceException("User ID not found in security context");
        }

        ApiResponse<List<Long>> resp = fetcher.apply(userId);

        if (resp == null || !resp.getSuccess()) {
            throw new ExternalServiceException(
                    resp != null ? resp.getMessage() :
                            "Failed to fetch " + type + " group IDs"
            );
        }

        return resp.getData() != null ? resp.getData() : List.of();
    }
}
