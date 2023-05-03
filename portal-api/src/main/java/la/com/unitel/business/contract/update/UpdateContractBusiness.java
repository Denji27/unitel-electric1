package la.com.unitel.business.contract.update;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.Constants;
import la.com.unitel.Translator;
import la.com.unitel.business.contract.dto.ContractDetail;
import la.com.unitel.business.contract.dto.projection.ContractDetailView;
import la.com.unitel.business.contract.update.dto.UpdateContractRequest;
import la.com.unitel.entity.account.MeterDevice;
import la.com.unitel.entity.account.ReaderContractId;
import la.com.unitel.entity.account.ReaderContractMap;
import la.com.unitel.entity.constant.Gender;
import la.com.unitel.entity.edl.District;
import la.com.unitel.entity.usage_payment.Contract;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

/**
 * @author : Tungct
 * @since : 5/3/2023, Wed
 **/
@Service
@Slf4j
public class UpdateContractBusiness extends BaseBusiness implements IUpdateContract {
    @Override
    public CommonResponse onUpdateContract(String contractId, UpdateContractRequest updateContractRequest, Principal principal) {
        Contract contract = baseService.getContractService().findById(contractId);
        if (contract == null)
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        District district = baseService.getDistrictService().findById(updateContractRequest.getDistrictId());
        if (district == null)
            throw new ErrorCommon(ErrorCode.DISTRICT_INVALID, Translator.toLocale(ErrorCode.DISTRICT_INVALID));

        boolean isValidContractType = baseService.getContractTypeService().existsByCode(updateContractRequest.getContractType());
        if (!isValidContractType)
            throw new ErrorCommon(ErrorCode.CONTRACT_TYPE_INVALID, Translator.toLocale(ErrorCode.CONTRACT_TYPE_INVALID));

        /*Account account = accountService.findById(contract.getAccountId());
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
        if (updateContractRequest.getAddress() != null) account.setAddress(updateContractRequest.getAddress());
        if (updateContractRequest.getRemark() != null) account.setRemark(updateContractRequest.getRemark());
        account.setUpdatedBy(principal.getName());
        account = accountService.save(account);*/

        contract.setPhoneNumber(baseService.getUtil().toMsisdn(updateContractRequest.getPhoneNumber()));
        contract.setDistrictId(updateContractRequest.getDistrictId());
        contract.setProvinceId(district.getProvinceId());
        contract.setContractType(updateContractRequest.getContractType());
        contract.setGender(Gender.findByName(updateContractRequest.getGender()));
        contract.setAvatarId(contract.getGender().equals(Gender.MALE) ? Constants.AVATAR_MALE_DEFAULT : Constants.AVATAR_FEMALE_DEFAULT);
        if (updateContractRequest.getLatitude() != null) contract.setLatitude(updateContractRequest.getLatitude());
        if (updateContractRequest.getLongitude() != null) contract.setLongitude(updateContractRequest.getLongitude());
        contract.setIsActive(true);
        if (updateContractRequest.getAddress() != null) contract.setAddress(updateContractRequest.getAddress());
        if (updateContractRequest.getRemark() != null) contract.setRemark(updateContractRequest.getRemark());
        contract.setUpdatedBy(principal.getName());
        contract = baseService.getContractService().save(contract);

        return generateSuccessResponse(updateContractRequest.getRequestId(),
                ContractDetail.generate(baseService.getContractService().findContractDetail(contract.getId(), ContractDetailView.class)));
    }

    @Override
    public CommonResponse onUploadAvatar(String contractId, MultipartFile file, Principal principal) {
        Contract contract = baseService.getContractService().findById(contractId);
        if (contract == null)
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

       /* Account account = accountService.findById(contract.getAccountId());
        if (account == null)
            throw new ErrorCommon(ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));*/

        String fileId = null;

        try {
            fileId = baseService.getStorageService().uploadFile(Constants.ELECTRIC, file, CannedAccessControlList.PublicRead);
        } catch (IOException e) {
            log.error("Upload file error due to ", e);
        }

        if (fileId == null)
            throw new ErrorCommon(ErrorCode.FILE_UPLOAD_ERROR, Translator.toLocale(ErrorCode.FILE_UPLOAD_ERROR));

        /*if (account.getAvatarId() != null) {
            log.info("Delete old avatar");
            String[] split = account.getAvatarId().split("/");
            storageService.deleteFile(Constant.ELECTRIC, split[split.length - 1]);
        }

        account.setAvatarId("https://s3.mytel.com.mm/electric/" + fileId);
        account.setUpdatedBy(principal.getName());
        account = accountService.save(account);*/

        contract.setUpdatedBy(principal.getName());
        contract = baseService.getContractService().save(contract);

        return generateSuccessResponse(UUID.randomUUID().toString(),
                ContractDetail.generate(baseService.getContractService().findContractDetail(contract.getId(), ContractDetailView.class)));
    }

}
