package la.com.unitel.business.contract;

import la.com.unitel.business.*;
import la.com.unitel.business.account.dto.AccountDetail;
import la.com.unitel.business.account.view.AccountDetailView;
import la.com.unitel.business.contract.dto.ContractDetail;
import la.com.unitel.business.contract.dto.CreateContractRequest;
import la.com.unitel.business.contract.dto.UpdateContractRequest;
import la.com.unitel.business.contract.view.ContractDetailView;
import la.com.unitel.entity.account.*;
import la.com.unitel.entity.constant.Gender;
import la.com.unitel.entity.usage_payment.Contract;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import la.com.unitel.exception.validation.GenderRegex;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class ContractBusiness extends BaseBusiness implements IContract {
    @Autowired
    private KeycloakUtil keycloakUtil;

    @Override
    @Transactional
    public CommonResponse onCreateContract(CreateContractRequest createContractRequest, Principal principal) {
        if (accountService.existsByUsername(createContractRequest.getUsername()))
            throw new ErrorCommon(ErrorCode.USERNAME_EXISTED, Translator.toLocale(ErrorCode.USERNAME_EXISTED));

        District district = districtService.findById(createContractRequest.getDistrictId());
        if (district == null)
            throw new ErrorCommon(ErrorCode.DISTRICT_INVALID, Translator.toLocale(ErrorCode.DISTRICT_INVALID));

        boolean isValidContractType = contractTypeService.existsByCode(createContractRequest.getContractType());
        if (!isValidContractType)
            throw new ErrorCommon(ErrorCode.CONTRACT_TYPE_INVALID, Translator.toLocale(ErrorCode.CONTRACT_TYPE_INVALID));

        MeterDevice device = deviceService.findById(createContractRequest.getDeviceId());
        if (device == null)
            throw new ErrorCommon(ErrorCode.DEVICE_INVALID, Translator.toLocale(ErrorCode.DEVICE_INVALID));

        //TODO validate readerId & contractId

        UserRepresentation keycloakUser = keycloakUtil.createUser(createContractRequest.getUsername(), createContractRequest.getPassword(),
                util.toMsisdn(createContractRequest.getPhoneNumber()), district.getName(), createContractRequest.getContractType());
        if (keycloakUser == null)
            throw new ErrorCommon(ErrorCode.KEYCLOAK_CREATE_FAILED, Translator.toLocale(ErrorCode.KEYCLOAK_CREATE_FAILED));

        Account account = new Account();
        account.setId(keycloakUser.getId());
        account.setUsername(createContractRequest.getUsername());
        account.setPhoneNumber(util.toMsisdn(createContractRequest.getPhoneNumber()));
        account.setAvatarId(createContractRequest.getAvatarId());
        account.setDistrictId(createContractRequest.getDistrictId());
        account.setGender(Gender.findByName(createContractRequest.getGender()));
        account.setAddress(createContractRequest.getAddress());
        account.setRemark(createContractRequest.getRemark());
        account.setCreatedBy(principal.getName());
        account.setIsActive(true);
        account = accountService.save(account);

        RoleAccount roleAccount = new RoleAccount();
        roleAccount.setId(new RoleAccountId(Constant.ENDUSER, account.getId()));
        roleAccount.setCreatedBy(principal.getName());
        roleAccount = roleService.saveRoleAccount(roleAccount);

        Contract contract = new Contract();
        contract.setName(createContractRequest.getName());
        contract.setAccountId(account.getId());
        contract.setDistrictId(createContractRequest.getDistrictId());
        contract.setProvinceId(district.getProvinceId());
        contract.setContractType(createContractRequest.getContractType());
        contract.setLatitude(createContractRequest.getLatitude());
        contract.setLongitude(createContractRequest.getLongitude());
        contract.setMeterCode(createContractRequest.getDeviceId());
        contract.setIsActive(true);
        contract.setAddress(createContractRequest.getAddress());
        contract.setRemark(createContractRequest.getRemark());
        contract.setCreatedBy(principal.getName());
        contract = contractService.save(contract);

        ReaderContractMap readerContractMap = new ReaderContractMap();
        readerContractMap.setId(new ReaderContractId(createContractRequest.getReaderId(), contract.getId()));
        readerContractMap.setRole(Constant.READER);
        readerContractMap.setCreatedBy(principal.getName());
        contractService.save(readerContractMap);

        ReaderContractMap cashierContractMap = new ReaderContractMap();
        cashierContractMap.setId(new ReaderContractId(createContractRequest.getCashierId(), contract.getId()));
        cashierContractMap.setRole(Constant.CASHIER);
        cashierContractMap.setCreatedBy(principal.getName());
        contractService.save(cashierContractMap);

        return generateSuccessResponse(createContractRequest.getRequestId(),
                ContractDetail.generate(contractService.findContractDetail(contract.getId(), ContractDetailView.class)));
    }

    @Override
    public CommonResponse onUpdateContract(String contractId, UpdateContractRequest updateContractRequest, Principal principal) {
        Contract contract = contractService.findById(contractId);
        if (contract == null)
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        District district = districtService.findById(updateContractRequest.getDistrictId());
        if (district == null)
            throw new ErrorCommon(ErrorCode.DISTRICT_INVALID, Translator.toLocale(ErrorCode.DISTRICT_INVALID));

        boolean isValidContractType = contractTypeService.existsByCode(updateContractRequest.getContractType());
        if (!isValidContractType)
            throw new ErrorCommon(ErrorCode.CONTRACT_TYPE_INVALID, Translator.toLocale(ErrorCode.CONTRACT_TYPE_INVALID));

        Account account = accountService.findById(contract.getAccountId());
        if (account == null)
            throw new ErrorCommon(ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        UserRepresentation keycloakUser = keycloakUtil.findByUsername(account.getUsername());
        if (keycloakUser == null)
            throw new ErrorCommon(ErrorCode.KEYCLOAK_USER_NOT_FOUND, Translator.toLocale(ErrorCode.KEYCLOAK_USER_NOT_FOUND));

        boolean isUserUpdated = keycloakUtil.updateUser(keycloakUser.getId(), updateContractRequest.getPassword(),
                updateContractRequest.getPhoneNumber(), district.getName(), updateContractRequest.getContractType());
        if (!isUserUpdated)
            throw new ErrorCommon(ErrorCode.KEYCLOAK_UPDATE_FAILED, Translator.toLocale(ErrorCode.KEYCLOAK_UPDATE_FAILED));

        account.setPhoneNumber(util.toMsisdn(updateContractRequest.getPhoneNumber()));
        account.setDistrictId(updateContractRequest.getDistrictId());
        account.setGender(Gender.findByName(updateContractRequest.getGender()));
        account.setAvatarId(updateContractRequest.getAvatarId());
        if (updateContractRequest.getAddress() != null) account.setAddress(updateContractRequest.getAddress());
        if (updateContractRequest.getRemark() != null) account.setRemark(updateContractRequest.getRemark());
        account.setUpdatedBy(principal.getName());
        account = accountService.save(account);

        contract.setAccountId(account.getId());
        contract.setDistrictId(updateContractRequest.getDistrictId());
        contract.setProvinceId(district.getProvinceId());
        contract.setContractType(updateContractRequest.getContractType());
        if (updateContractRequest.getLatitude() != null) contract.setLatitude(updateContractRequest.getLatitude());
        if (updateContractRequest.getLongitude() != null) contract.setLongitude(updateContractRequest.getLongitude());
        contract.setIsActive(true);
        if (updateContractRequest.getAddress() != null) contract.setAddress(updateContractRequest.getAddress());
        if (updateContractRequest.getRemark() != null) contract.setRemark(updateContractRequest.getRemark());
        contract.setUpdatedBy(principal.getName());
        contract = contractService.save(contract);

        return generateSuccessResponse(updateContractRequest.getRequestId(),
                ContractDetail.generate(contractService.findContractDetail(contract.getId(), ContractDetailView.class)));
    }

    @Override
    public CommonResponse onViewContractDetail(String contractId) {
        Contract contract = contractService.findById(contractId);
        if (contract == null)
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));
        return generateSuccessResponse(UUID.randomUUID().toString(),
                ContractDetail.generate(contractService.findContractDetail(contract.getId(), ContractDetailView.class)));
    }

    @Override
    public CommonResponse onSearchContract(String input, Pageable pageable) {
        Page<ContractDetailView> views = contractService.searchContractDetail(input, ContractDetailView.class, pageable);
        List<ContractDetail> collect = views.getContent().parallelStream().map(ContractDetail::generate).collect(Collectors.toList());
        Page<ContractDetail> result = new PageImpl<>(collect, pageable, views.getTotalElements());
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }
}
