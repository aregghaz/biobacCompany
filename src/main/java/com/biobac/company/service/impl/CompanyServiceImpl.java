package com.biobac.company.service.impl;

import com.biobac.company.client.AttributeClient;
import com.biobac.company.entity.Company;
import com.biobac.company.mapper.CompanyMapper;
import com.biobac.company.repository.ClientTypeRepository;
import com.biobac.company.repository.CompanyGroupRepository;
import com.biobac.company.repository.CompanyRepository;
import com.biobac.company.repository.CompanyTypeRepository;
import com.biobac.company.repository.ConditionsRepository;
import com.biobac.company.repository.ContractFormRepository;
import com.biobac.company.repository.CooperationRepository;
import com.biobac.company.repository.DeliveryMethodRepository;
import com.biobac.company.repository.DeliveryPayerRepository;
import com.biobac.company.repository.DetailsRepository;
import com.biobac.company.repository.FinancialTermsRepository;
import com.biobac.company.repository.LineRepository;
import com.biobac.company.repository.RegionRepository;
import com.biobac.company.repository.SaleTypeRepository;
import com.biobac.company.request.CompanyRequest;
import com.biobac.company.response.CompanyResponse;
import com.biobac.company.service.CompanyService;
import com.biobac.company.utils.GroupUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyTypeRepository companyTypeRepository;
    private final RegionRepository regionRepository;
    private final CompanyMapper companyMapper;
    private final AttributeClient attributeClient;
    private final SaleTypeRepository saleTypeRepository;
    private final CompanyGroupRepository companyGroupRepository;
    private final DetailsRepository detailsRepository;
    private final ConditionsRepository conditionsRepository;
    private final ContractFormRepository contractFormRepository;
    private final FinancialTermsRepository financialTermsRepository;
    private final DeliveryPayerRepository deliveryPayerRepository;
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final LineRepository lineRepository;
    private final CooperationRepository cooperationRepository;
    private final ClientTypeRepository clientTypeRepository;
    private final GroupUtil groupUtil;

    @Override
    public CompanyResponse registerCompany(CompanyRequest request) {
        Company company = companyMapper.toCompanyEntity(request);
        Company savedCompany = companyRepository.save(company);
        return companyMapper.toCompanyResponse(savedCompany);
    }

    public CompanyResponse getCompanyById(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        return company.map(companyMapper::toCompanyResponse).orElseThrow();
    }


//    @Override
//    @Transactional
//    public CompanyResponse registerCompany(CompanyRequest request) {
//        if (companyRepository.existsByName(request.getName())) {
//            throw new DuplicateException("Company with name " + request.getName() + " already exists.");
//        }
//
//        Company company = companyMapper.toCompanyEntity(request);
//
//        setCompanyRelations(company, request);
//
//        Company savedCompany = companyRepository.save(company);
//
//        if (request.getDetail() != null) {
//            updateOrCreateDetails(savedCompany, request.getDetail());
//        }
//
//        if (request.getCondition() != null) {
//            updateOrCreateConditions(savedCompany, request.getCondition());
//        }
//
//        if (request.getAttributes() != null && !request.getAttributes().isEmpty()) {
//            attributeClient.createValues(
//                    savedCompany.getId(),
//                    AttributeTargetType.COMPANY.name(),
//                    request.getAttributes()
//            );
//        }
//
//        return companyMapper.toResponse(savedCompany);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public CompanyResponse getCompany(Long companyId) {
//        return companyRepository.findById(companyId)
//                .map(companyMapper::toResponse)
//                .orElseThrow(() -> new NotFoundException("Company with ID " + companyId + " does not exist."));
//    }
//
//    @Override
//    @Transactional
//    public CompanyResponse updateCompany(Long id, CompanyRequest request) {
//        Company company = companyRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Company with ID " + id + " does not exist."));
//
//        if (companyRepository.existsByNameAndIdNot(request.getName(), id)) {
//            throw new DuplicateException("Company with name " + request.getName() + " already exists.");
//        }
//
//        companyMapper.updateEntityFromDto(request, company);
//        setCompanyRelations(company, request);
//        Company updatedCompany = companyRepository.save(company);
//
//        if (request.getDetail() != null) {
//            updateOrCreateDetails(updatedCompany, request.getDetail());
//        }
//
//        if (request.getCondition() != null) {
//            updateOrCreateConditions(updatedCompany, request.getCondition());
//        }
//
//        List<AttributeUpsertRequest> attributes = request.getAttributeGroupIds() == null || request.getAttributeGroupIds().isEmpty() ? Collections.emptyList() : request.getAttributes();
//
//        attributeClient.updateValues(updatedCompany.getId(), AttributeTargetType.COMPANY.name(), request.getAttributeGroupIds(), attributes);
//
//        return companyMapper.toResponse(updatedCompany);
//    }
//
//    @Override
//    @Transactional
//    public void deleteCompany(Long companyId) {
//        Company company = companyRepository.findById(companyId)
//                .orElseThrow(() -> new NotFoundException("Company not found"));
//        company.setDeleted(true);
//        companyRepository.save(company);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<CompanyResponse> listAllCompanies() {
//        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
//        Specification<Company> spec = CompanySpecification.isDeleted()
//                .and(CompanySpecification.belongsToGroups(groupIds));
//        return companyRepository.findAll(spec)
//                .stream()
//                .map(companyMapper::toResponse)
//                .toList();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Pair<List<CompanyResponse>, PaginationMetadata> listCompaniesWithPagination(Integer page, Integer size, String sortBy, String sortDir, Map<String, FilterCriteria> filters) {
//        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
//        Sort sort = sortDir.equalsIgnoreCase("asc") ?
//                Sort.by(sortBy).ascending() :
//                Sort.by(sortBy).descending();
//
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        Specification<Company> spec = CompanySpecification.buildSpecification(filters)
//                .and(CompanySpecification.belongsToGroups(groupIds));
//
//        Page<Company> companyPage = companyRepository.findAll(spec, pageable);
//
//        List<CompanyResponse> content = companyPage.getContent()
//                .stream()
//                .map(companyMapper::toResponse)
//                .toList();
//
//        PaginationMetadata metadata = new PaginationMetadata(
//                companyPage.getNumber(),
//                companyPage.getSize(),
//                companyPage.getTotalElements(),
//                companyPage.getTotalPages(),
//                companyPage.isLast(),
//                filters,
//                sortDir,
//                sortBy,
//                "companyTable"
//        );
//
//        return Pair.of(content, metadata);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public String getCompanyName(Long id) {
//        String name = companyRepository.findCompanyNameById(id);
//        if (name == null) {
//            throw new NotFoundException("Company with ID " + id + " does not exist.");
//        }
//        return name;
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<CompanyResponse> listAllBuyersCompanies() {
//        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
//        Specification<Company> spec = CompanySpecification.isDeleted()
//                .and(CompanySpecification.belongsToGroups(groupIds))
//                .and(CompanySpecification.filterBuyer());
//        return companyRepository.findAll(spec)
//                .stream()
//                .map(companyMapper::toResponse)
//                .toList();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<CompanyResponse> listAllSellersCompanies() {
//        List<Long> groupIds = groupUtil.getAccessibleCompanyGroupIds();
//        Specification<Company> spec = CompanySpecification.isDeleted()
//                .and(CompanySpecification.belongsToGroups(groupIds))
//                .and(CompanySpecification.filterSeller());
//        return companyRepository.findAll(spec)
//                .stream()
//                .map(companyMapper::toResponse)
//                .toList();
//    }
//
//    private void setCompanyRelations(Company company, CompanyRequest request) {
//
//        if (request.getCompanyGroupId() != null) {
//            CompanyGroup group = companyGroupRepository.findById(request.getCompanyGroupId())
//                    .orElseThrow(() -> new NotFoundException("Company group not found"));
//            company.setCompanyGroup(group);
//        }
//
//        if (request.getRegionId() != null) {
//            Region region = regionRepository.findById(request.getRegionId())
//                    .orElseThrow(() -> new NotFoundException("Region not found"));
//            company.setRegion(region);
//        }
//
//        if (request.getSaleTypeId() != null) {
//            SaleType saleType = saleTypeRepository.findById(request.getSaleTypeId())
//                    .orElseThrow(() -> new NotFoundException("Sale type not found"));
//            company.setSaleType(saleType);
//        }
//
//        if (request.getTypeIds() != null) {
//            List<CompanyType> types = companyMapper.mapTypeIds(
//                    request.getTypeIds(),
//                    companyTypeRepository
//            );
//            company.setTypes(types);
//        }
//
//        if (request.getAttributeGroupIds() != null) {
//            company.setAttributeGroupIds(request.getAttributeGroupIds());
//        }
//    }
//
//
//    private void updateOrCreateDetails(Company company, DetailsRequest dto) {
//        Detail detail = detailsRepository.findByCompanyId(company.getId())
//                .orElseGet(Detail::new);
//
//        detail.setCompany(company);
//        detail.setInn(dto.getInn());
//        detail.setKpp(dto.getKpp());
//        detail.setOgrn(dto.getOgrn());
//        detail.setOkpo(dto.getOkpo());
//        detail.setBankAccount(dto.getBankAccount());
//        detail.setBik(dto.getBik());
//        detail.setKs(dto.getKs());
//        detail.setBankName(dto.getBankName());
//
//        detailsRepository.save(detail);
//    }
//
//    private void updateOrCreateConditions(Company company, ConditionsRequest dto) {
//        Condition condition = conditionsRepository.findByCompanyId(company.getId())
//                .orElseGet(Condition::new);
//
//        condition.setCompany(company);
//        condition.setDeliveryMethod(findDeliveryMethod(dto.getDeliveryMethodId()));
//        condition.setDeliveryPayer(findDeliveryPayer(dto.getDeliveryPayerId()));
//        condition.setFinancialTerms(findFinancialTerms(dto.getFinancialTermsId()));
//        condition.setContractForm(findContractForm(dto.getContractFormId()));
//        condition.setBonus(dto.getBonus());
//
//        conditionsRepository.save(condition);
//    }
//
//    private DeliveryMethod findDeliveryMethod(Long id) {
//        return deliveryMethodRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("DeliveryMethod not found with id " + id));
//    }
//
//    private DeliveryPayer findDeliveryPayer(Long id) {
//        return deliveryPayerRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("DeliveryPayer not found with id " + id));
//    }
//
//    private FinancialTerms findFinancialTerms(Long id) {
//        return financialTermsRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("FinancialTerms not found with id " + id));
//    }
//
//    private ContractForm findContractForm(Long id) {
//        return contractFormRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("ContractForm not found with id " + id));
//    }
}
