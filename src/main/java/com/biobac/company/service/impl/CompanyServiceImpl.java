package com.biobac.company.service.impl;

import com.biobac.company.client.AttributeClient;
import com.biobac.company.dto.PaginationMetadata;
import com.biobac.company.entity.Company;
import com.biobac.company.entity.Condition;
import com.biobac.company.entity.ContractForm;
import com.biobac.company.entity.DeliveryMethod;
import com.biobac.company.entity.DeliveryPayer;
import com.biobac.company.entity.Detail;
import com.biobac.company.entity.FinancialTerms;
import com.biobac.company.entity.embeddable.Address;
import com.biobac.company.entity.enums.AttributeTargetType;
import com.biobac.company.exception.DuplicateException;
import com.biobac.company.exception.NotFoundException;
import com.biobac.company.mapper.CompanyMapper;
import com.biobac.company.repository.CompanyRepository;
import com.biobac.company.repository.ConditionsRepository;
import com.biobac.company.repository.ContractFormRepository;
import com.biobac.company.repository.DeliveryMethodRepository;
import com.biobac.company.repository.DeliveryPayerRepository;
import com.biobac.company.repository.DetailsRepository;
import com.biobac.company.repository.FinancialTermsRepository;
import com.biobac.company.request.AttributeUpsertRequest;
import com.biobac.company.request.CompanyRequest;
import com.biobac.company.request.ConditionsRequest;
import com.biobac.company.request.DetailRequest;
import com.biobac.company.request.FilterCriteria;
import com.biobac.company.response.CompanyResponse;
import com.biobac.company.service.CompanyService;
import com.biobac.company.utils.GroupUtil;
import com.biobac.company.utils.specifications.CompanySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final ConditionsRepository conditionsRepository;
    private final DeliveryPayerRepository deliveryPayerRepository;
    private final FinancialTermsRepository financialTermsRepository;
    private final ContractFormRepository contractFormRepository;
    private final DetailsRepository detailRepository;
    private final AttributeClient attributeClient;
    private final GroupUtil groupUtil;

    @Override
    @Transactional
    public CompanyResponse registerCompany(CompanyRequest request) {
        Company company = companyMapper.toCompanyEntity(request);
        Condition condition = createCondition(request.getCondition(), company);
        Detail detail = createDetail(request, company);
        Company savedCompany = companyRepository.save(company);
        company.setCondition(condition);
        company.setDetail(detail);
        return companyMapper.toCompanyResponse(savedCompany);
    }

    @Transactional(readOnly = true)
    public CompanyResponse getCompanyById(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        return company.map(companyMapper::toCompanyResponse).orElseThrow();
    }

    @Override
    @Transactional
    public CompanyResponse updateCompany(Long id, CompanyRequest request) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company with ID " + id + " does not exist."));

        Company updateCompany = companyMapper.toUpdateCompany(request, company.getId());

        if (companyRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new DuplicateException("Company with name " + request.getName() + " already exists.");
        }

        Company updatedCompany = companyRepository.save(updateCompany);

        if (request.getDetail() != null) {
            updateOrCreateDetails(updatedCompany, request.getDetail());
        }

        if (request.getCondition() != null) {
            updateOrCreateConditions(updatedCompany, request.getCondition());
        }

        List<AttributeUpsertRequest> attributes = request.getAttributeGroupIds() == null || request.getAttributeGroupIds().isEmpty()
                ? Collections.emptyList()
                : request.getAttributes();

        attributeClient.updateValues(updatedCompany.getId(), AttributeTargetType.COMPANY.name(), request.getAttributeGroupIds(), attributes);

        return companyMapper.toCompanyResponse(updatedCompany);
    }

    @Override
    @Transactional
    public void deleteCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company not found"));
        company.setDeleted(true);
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
                .map(companyMapper::toCompanyResponse)
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
                .map(companyMapper::toCompanyResponse)
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
    public List<CompanyResponse> listAllBuyersCompanies() {
        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
        Specification<Company> spec = CompanySpecification.isDeleted()
                .and(CompanySpecification.belongsToGroups(groupIds))
                .and(CompanySpecification.filterBuyer());
        return companyRepository.findAll(spec)
                .stream()
                .map(companyMapper::toCompanyResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> listAllSellersCompanies() {
        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
        Specification<Company> spec = CompanySpecification.isDeleted()
                .and(CompanySpecification.belongsToGroups(groupIds))
                .and(CompanySpecification.filterSeller());
        return companyRepository.findAll(spec)
                .stream()
                .map(companyMapper::toCompanyResponse)
                .toList();
    }


    private Detail createDetail(CompanyRequest request, Company company) {
        Detail detail = Detail.builder()
                .bankAccount(request.getDetail().getBankAccount())
                .bik(request.getDetail().getBik())
                .ks(request.getDetail().getKs())
                .bankName(request.getDetail().getBankName())
                .ogrn(request.getDetail().getOgrn())
                .okpo(request.getDetail().getOkpo())
                .kpp(request.getDetail().getKpp())
                .inn(request.getDetail().getInn())
                .company(company)
                .build();
        return detailRepository.save(detail);
    }

    private Condition createCondition(ConditionsRequest request, Company company) {
        Condition condition = Condition.builder()
                .deliveryMethods(createDeliveryMethod(request.getDeliveryMethodIds()))
                .deliveryPayer(createDeliveryPayer(request.getDeliveryPayerId()))
                .financialTerms(createFinancialTerms(request.getFinancialTermIds()))
                .contractForm(createContractForm(request.getContractFormId()))
                .company(company)
                .bonus(request.getBonus())
                .build();
        return conditionsRepository.save(condition);
    }

    private List<DeliveryMethod> createDeliveryMethod(List<Long> id) {
        return deliveryMethodRepository.findAllById(id);
    }

    private DeliveryPayer createDeliveryPayer(Long id) {
        return deliveryPayerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    private List<FinancialTerms> createFinancialTerms(List<Long> id) {
        return financialTermsRepository.findAllById(id);
    }

    private ContractForm createContractForm(Long id) {
        return contractFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    private void updateOrCreateDetails(Company company, DetailRequest dto) {
        Detail detail = detailRepository.findByCompanyId(company.getId())
                .orElseGet(Detail::new);

        detail.setCompany(company);
        detail.setInn(dto.getInn());
        detail.setKpp(dto.getKpp());
        detail.setOgrn(dto.getOgrn());
        detail.setOkpo(dto.getOkpo());
        detail.setBankAccount(dto.getBankAccount());
        detail.setBik(dto.getBik());
        detail.setKs(dto.getKs());
        detail.setBankName(dto.getBankName());

        detailRepository.save(detail);
    }

    private void updateOrCreateConditions(Company company, ConditionsRequest dto) {
        Condition condition = conditionsRepository.findByCompanyId(company.getId())
                .orElseGet(Condition::new);

        condition.setCompany(company);
        condition.setDeliveryMethods(createDeliveryMethod(dto.getDeliveryMethodIds()));
        condition.setDeliveryPayer(createDeliveryPayer(dto.getDeliveryPayerId()));
        condition.setFinancialTerms(createFinancialTerms(dto.getFinancialTermIds()));
        condition.setContractForm(createContractForm(dto.getContractFormId()));
        condition.setBonus(dto.getBonus());

        conditionsRepository.save(condition);
    }
}