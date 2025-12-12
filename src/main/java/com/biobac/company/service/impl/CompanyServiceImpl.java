package com.biobac.company.service.impl;

import com.biobac.company.client.AttributeClient;
import com.biobac.company.client.ProductClient;
import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.*;
import com.biobac.company.entity.enums.AttributeTargetType;
import com.biobac.company.exception.DuplicateException;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.CompanyMapper;
import com.biobac.company.mapper.PriceListWrapperMapper;
import com.biobac.company.repository.CompanyRepository;
import com.biobac.company.repository.ContactPersonRepository;
import com.biobac.company.repository.OurCompanyRepository;
import com.biobac.company.repository.PriceListWrapperRepository;
import com.biobac.company.request.AttributeUpsertRequest;
import com.biobac.company.request.BranchRequest;
import com.biobac.company.request.CompanyRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.CompanyResponse;
import com.biobac.company.response.OurCompanyResponse;
import com.biobac.company.response.PriceListWrapperResponse;
import com.biobac.company.response.ProductResponse;
import com.biobac.company.service.BranchService;
import com.biobac.company.service.CompanyService;
import com.biobac.company.service.ConditionService;
import com.biobac.company.service.DetailService;
import com.biobac.company.utils.GroupUtil;
import com.biobac.company.utils.ProductClientUtil;
import com.biobac.company.utils.specifications.CompanySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final DetailService detailService;
    private final ConditionService conditionService;
    private final AttributeClient attributeClient;
    private final GroupUtil groupUtil;
    private final ContactPersonRepository contactPersonRepository;
    private final BranchService branchService;
    private final ProductClient productClient;
    private final PriceListWrapperMapper priceListWrapperMapper;
    private final PriceListWrapperRepository priceListWrapperRepository;
    private final OurCompanyRepository ourCompanyRepository;

    @Override
    @Transactional
    public CompanyResponse registerCompany(CompanyRequest request) {

        List<ContactPerson> contactPersons = request.getContactPersonIds() == null
                ? Collections.emptyList()
                : contactPersonRepository.findAllById(request.getContactPersonIds());

        List<OurCompany> ourCompanies = request.getOurCompanyIds() != null
                ? ourCompanyRepository.findAllById(request.getOurCompanyIds())
                : Collections.emptyList();

        Company company = companyMapper.toCompanyEntity(request);
        Condition condition = request.getCondition() != null
                ? conditionService.createCondition(request.getCondition(), company)
                : new Condition();

        Detail detail = request.getDetail() != null
                ? detailService.createDetail(request.getDetail(), company)
                : new Detail();

        PriceListWrapper priceListWrapper = request.getPriceListId() != null
                ? priceListWrapperRepository.findById(request.getPriceListId()).orElseThrow()
                : null;

        List<Branch> branches = new ArrayList<>();

        if (request.getBranches() != null && !request.getBranches().isEmpty()) {
            request.getBranches().forEach(branchRequest -> {
                Branch newBranch = branchService.createBranchForCompany(branchRequest, company);
                branches.add(newBranch);
            });
        }

        company.setCondition(condition);
        company.setBranches(branches);
        company.setDetail(detail);
        company.setContactPerson(contactPersons);
        company.setPriceList(priceListWrapper);
        company.setOurCompanies(ourCompanies);
        Company savedCompany = companyRepository.save(company);

        List<AttributeUpsertRequest> attributes =
                request.getAttributeGroupIds() == null || request.getAttributeGroupIds().isEmpty()
                        ? Collections.emptyList()
                        : request.getAttributes();

        attributeClient.updateValues(
                savedCompany.getId(),
                AttributeTargetType.COMPANY.name(),
                request.getAttributeGroupIds(), attributes
        );

        return getCompanyResponse(savedCompany);
    }

    @Transactional(readOnly = true)
    public CompanyResponse getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Company with id %s not found", id)));
        return getCompanyResponse(company);
    }

    @Override
    public CompanyResponse getCompanyByHistoryId(Long historyId) {
        return companyRepository.findByHistoryId(historyId)
                .map(this::getCompanyResponse)
                .orElseThrow(() -> new NotFoundException("Company not found with history id: " + historyId));
    }

    @Override
    public Company fetchCompanyById(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company not found with id: " + companyId));
    }

    public Company getCompanyEntityById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Company with id %s not found", id)));
    }

    @Override
    @Transactional
    public CompanyResponse updateCompany(Long id, CompanyRequest request) {
        List<ContactPerson> contactPersons = request.getContactPersonIds() == null
                ? Collections.emptyList()
                : contactPersonRepository.findAllById(request.getContactPersonIds());

        Company company = getCompanyEntityById(id);
        Condition condition = conditionService.updatedCondition(company.getId(), request.getCondition(), company);
        Detail detail = detailService.updateDetail(company.getId(), request.getDetail(), company);
        List<Branch> branches = new ArrayList<>();

        syncBranches(request, company, branches);

        company.setContactPerson(contactPersons);
        company.setCondition(condition);
        company.setDetail(detail);

        Company updateCompany = companyMapper.toUpdateCompany(request, company);

        if (companyRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new DuplicateException("Company with name " + request.getName() + " already exists.");
        }

        Company updatedCompany = companyRepository.save(updateCompany);

        List<AttributeUpsertRequest> attributes =
                (request.getAttributeGroupIds() == null || request.getAttributeGroupIds().isEmpty())
                        ? Collections.emptyList()
                        : request.getAttributes();

        attributeClient.updateValues(
                updatedCompany.getId(),
                AttributeTargetType.COMPANY.name(),
                request.getAttributeGroupIds(), attributes
        );

        return getCompanyResponse(updateCompany);
    }

    @Override
    @Transactional
    public void deleteCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company not found"));
        companyRepository.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> listAllCompanies() {
        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
        Specification<Company> spec = CompanySpecification.isDeleted()
                .and(CompanySpecification.belongsToGroups(groupIds));
        return companyRepository.findAll(spec)
                .stream()
                .map(this::getCompanyResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Pair<List<CompanyResponse>, PaginationMetadata> listCompaniesWithPagination(Integer page, Integer size, String sortBy, String sortDir, Map<String, FilterCriteria> filters) {
        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Company> spec = CompanySpecification.buildSpecification(filters)
                .and(CompanySpecification.belongsToGroups(groupIds));

        Page<Company> companyPage = companyRepository.findAll(spec, pageable);

        List<CompanyResponse> content = companyPage.getContent()
                .stream()
                .map(this::getCompanyResponse)
                .toList();

        PaginationMetadata metadata = new PaginationMetadata(
                companyPage.getNumber(),
                companyPage.getSize(),
                companyPage.getTotalElements(),
                companyPage.getTotalPages(),
                companyPage.isLast(),
                filters,
                sortDir,
                sortBy,
                "companyTable"
        );

        return Pair.of(content, metadata);
    }

    @Override
    @Transactional(readOnly = true)
    public String getCompanyName(Long id) {
        String name = companyRepository.findCompanyNameById(id);
        if (name == null) {
            throw new NotFoundException("Company with ID " + id + " does not exist.");
        }
        return name;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> listBuyerCompaniesWithCooperation() {
        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
        Specification<Company> spec = CompanySpecification.isDeleted()
                .and(CompanySpecification.belongsToGroups(groupIds))
                .and(CompanySpecification.filterByCooperation())
                .and(CompanySpecification.filterBuyer());
        return companyRepository.findAll(spec)
                .stream()
                .map(this::getCompanyResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> listSellerCompaniesWithCooperation() {
        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
        Specification<Company> spec = CompanySpecification.isDeleted()
                .and(CompanySpecification.belongsToGroups(groupIds))
                .and(CompanySpecification.filterByCooperation())
                .and(CompanySpecification.filterSeller());
        return companyRepository.findAll(spec)
                .stream()
                .map(this::getCompanyResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> listAllCompaniesByBuyer() {
        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
        Specification<Company> spec = CompanySpecification.isDeleted()
                .and(CompanySpecification.filterBuyer())
                .and(CompanySpecification.belongsToGroups(groupIds));
        return companyRepository.findAll(spec)
                .stream()
                .map(this::getCompanyResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> listAllCompaniesBySeller() {
        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
        Specification<Company> spec = CompanySpecification.isDeleted()
                .and(CompanySpecification.filterSeller())
                .and(CompanySpecification.belongsToGroups(groupIds));
        return companyRepository.findAll(spec)
                .stream()
                .map(this::getCompanyResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> getBuyerCompaniesByLines(List<Long> lineIds) {
        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
        Specification<Company> spec = CompanySpecification.isDeleted()
                .and(CompanySpecification.belongsToGroups(groupIds))
                .and(CompanySpecification.filterByCooperation())
                .and(CompanySpecification.filterBuyer());
        if (lineIds != null && !lineIds.isEmpty()) {
            spec = spec.and(CompanySpecification.filterByLines(lineIds));
        }
        return companyRepository.findAll(spec)
                .stream()
                .map(this::getCompanyResponse)
                .toList();
    }

    private void syncBranches(CompanyRequest request, Company company, List<Branch> branches) {
        if (request.getBranches() != null && !request.getBranches().isEmpty()) {
            Set<Long> requestBranchIds = request.getBranches().stream()
                    .map(BranchRequest::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            company.getBranches().removeIf(branch ->
                    branch.getId() != null && !requestBranchIds.contains(branch.getId()));

            Map<Long, Branch> existingBranchesMap = company.getBranches().stream()
                    .collect(Collectors.toMap(branch -> branch.getId(), branch -> branch));

            request.getBranches().forEach(branchRequest -> {
                Branch newBranch;
                if (branchRequest.getId() != null && existingBranchesMap.containsKey(branchRequest.getId())) {
                    newBranch = branchService.updateBranch(branchRequest.getId(), branchRequest, company);
                } else {
                    newBranch = branchService.createBranchForCompany(branchRequest, company);
                    company.getBranches().add(newBranch);
                }
                branches.add(newBranch);
            });
        }
    }

    private CompanyResponse getCompanyResponse(Company entity) {
        CompanyResponse response = companyMapper.toCompanyResponse(entity);
        if (entity.getPriceList() != null) {
            PriceListWrapper priceList = entity.getPriceList();
            List<ProductResponse> products = ProductClientUtil.enrichProductsWithPrices(priceList.getPriceListItems(), productClient);

            PriceListWrapperResponse priceListResponse = priceListWrapperMapper.toPriceListWrapperResponse(priceList);
            priceListResponse.setProduct(products);
            response.setPriceList(priceListResponse);
        }
        return response;
    }
}