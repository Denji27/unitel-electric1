package la.com.unitel.business.contract.create;

import la.com.unitel.*;
import la.com.unitel.business.account.dto.AccountDetail;
import la.com.unitel.business.account.view.AccountDetailView;
import la.com.unitel.business.contract.create.dto.CreateContractRequest;
import la.com.unitel.business.contract.dto.ContractDetail;
import la.com.unitel.business.contract.dto.projection.ContractDetailView;
import la.com.unitel.entity.account.*;
import la.com.unitel.entity.constant.Gender;
import la.com.unitel.entity.edl.District;
import la.com.unitel.entity.usage_payment.Contract;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 5/3/2023, Wed
 **/
@Service
@Slf4j
public class CreateContractBusiness extends BaseBusiness implements ICreateContract {
    @Override
    @Transactional
    public CommonResponse onCreateContract(CreateContractRequest createContractRequest, Principal principal) {
        /*if (baseService.getAccountService().existsByUsername(createContractRequest.getUsername()))
            throw new ErrorCommon(ErrorCode.USERNAME_EXISTED, Translator.toLocale(ErrorCode.USERNAME_EXISTED));*/

        District district = baseService.getDistrictService().findById(createContractRequest.getDistrictId());
        if (district == null)
            throw new ErrorCommon(ErrorCode.DISTRICT_INVALID, Translator.toLocale(ErrorCode.DISTRICT_INVALID));

        boolean isValidContractType = baseService.getContractTypeService().existsByCode(createContractRequest.getContractType());
        if (!isValidContractType)
            throw new ErrorCommon(ErrorCode.CONTRACT_TYPE_INVALID, Translator.toLocale(ErrorCode.CONTRACT_TYPE_INVALID));

        MeterDevice device = baseService.getDeviceService().findById(createContractRequest.getDeviceId());
        if (device == null || device.getContractId() != null)
            throw new ErrorCommon(ErrorCode.DEVICE_INVALID, Translator.toLocale(ErrorCode.DEVICE_INVALID));

        /*if (baseService.getContractService().existsByMeterCode(createContractRequest.getDeviceId()))
            throw new ErrorCommon(ErrorCode.DEVICE_EXISTED, Translator.toLocale(ErrorCode.DEVICE_EXISTED));*/

        if (baseService.getContractService().existsByContractName(createContractRequest.getName()))
            throw new ErrorCommon(ErrorCode.DEVICE_EXISTED, Translator.toLocale(ErrorCode.DEVICE_EXISTED));

        //TODO validate readerId & contractId active

        /*UserRepresentation keycloakUser = keycloakUtil.createUser(createContractRequest.getUsername(), createContractRequest.getPassword(),
                util.toMsisdn(createContractRequest.getPhoneNumber()), district.getName(), createContractRequest.getContractType());
        if (keycloakUser == null)
            throw new ErrorCommon(ErrorCode.KEYCLOAK_CREATE_FAILED, Translator.toLocale(ErrorCode.KEYCLOAK_CREATE_FAILED));

        Account account = new Account();
        account.setId(keycloakUser.getId());
        account.setUsername(createContractRequest.getUsername());
        account.setPhoneNumber(util.toMsisdn(createContractRequest.getPhoneNumber()));
        account.setDistrictId(createContractRequest.getDistrictId());
        account.setGender(Gender.findByName(createContractRequest.getGender()));
        account.setAvatarId(account.getGender().equals(Gender.MALE) ? Constant.AVATAR_MALE_DEFAULT : Constant.AVATAR_FEMALE_DEFAULT);
        account.setAddress(createContractRequest.getAddress());
        account.setRemark(createContractRequest.getRemark());
        account.setCreatedBy(principal.getName());
        account.setIsActive(true);
        account = accountService.save(account);*/

        /*RoleAccount roleAccount = new RoleAccount();
        roleAccount.setId(new RoleAccountId(Constant.ENDUSER, account.getId()));
        roleAccount.setCreatedBy(principal.getName());
        roleAccount = roleService.saveRoleAccount(roleAccount);*/

        Contract contract = new Contract();
        contract.setName(createContractRequest.getName());
        contract.setDistrictId(createContractRequest.getDistrictId());
        contract.setPhoneNumber(baseService.getUtil().toMsisdn(createContractRequest.getPhoneNumber()));
        contract.setProvinceId(district.getProvinceId());
        contract.setContractType(createContractRequest.getContractType());
        contract.setGender(Gender.findByName(createContractRequest.getGender()));
        contract.setAvatarId(contract.getGender().equals(Gender.MALE) ? Constants.AVATAR_MALE_DEFAULT : Constants.AVATAR_FEMALE_DEFAULT);
        contract.setLatitude(createContractRequest.getLatitude());
        contract.setLongitude(createContractRequest.getLongitude());
//        contract.setMeterCode(createContractRequest.getDeviceId());
        contract.setIsActive(true);
        contract.setAddress(createContractRequest.getAddress());
        contract.setRemark(createContractRequest.getRemark());
        contract.setCreatedBy(principal.getName());
        contract = baseService.getContractService().save(contract);

        ReaderContractMap readerContractMap = new ReaderContractMap();
        readerContractMap.setId(new ReaderContractId(createContractRequest.getReaderId(), contract.getId()));
        readerContractMap.setRole(Constants.READER);
        readerContractMap.setCreatedBy(principal.getName());
        baseService.getContractService().save(readerContractMap);

        ReaderContractMap cashierContractMap = new ReaderContractMap();
        cashierContractMap.setId(new ReaderContractId(createContractRequest.getCashierId(), contract.getId()));
        cashierContractMap.setRole(Constants.CASHIER);
        cashierContractMap.setCreatedBy(principal.getName());
        baseService.getContractService().save(cashierContractMap);

        device.setContractId(contract.getId());
        baseService.getDeviceService().save(device);

        return generateSuccessResponse(createContractRequest.getRequestId(),
                ContractDetail.generate(baseService.getContractService().findContractDetail(contract.getId(), ContractDetailView.class)));
    }
}
