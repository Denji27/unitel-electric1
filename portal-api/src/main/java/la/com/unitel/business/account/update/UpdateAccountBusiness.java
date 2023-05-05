package la.com.unitel.business.account.update;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import la.com.unitel.*;
import la.com.unitel.business.account.dto.AccountDetail;
import la.com.unitel.business.account.update.dto.UpdateAccountRequest;
import la.com.unitel.business.account.view.AccountDetailView;
import la.com.unitel.entity.account.Account;
import la.com.unitel.entity.account.RoleAccount;
import la.com.unitel.entity.account.RoleAccountId;
import la.com.unitel.entity.constant.Gender;
import la.com.unitel.entity.edl.District;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author : Tungct
 * @since : 5/3/2023, Wed
 **/
@Service
@Slf4j
public class UpdateAccountBusiness extends BaseBusiness implements IUpdateAccount {
    @Autowired
    private KeycloakUtil keycloakUtil;

    @Override
    @Transactional
    public CommonResponse onUpdateAccount(String accountId, UpdateAccountRequest updateAccountRequest, Principal principal) {
        Account account = baseService.getAccountService().findById(accountId);
        if (account == null)
            throw new ErrorCommon(ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        String invalidRole = updateAccountRequest.getRoleList().parallelStream().filter(role -> !baseService.getRoleService().existsByCode(role)).findFirst().orElse(null);
        if (invalidRole != null)
            throw new ErrorCommon(ErrorCode.ROLE_INVALID, Translator.toLocale(ErrorCode.ROLE_INVALID));

        District district = baseService.getDistrictService().findById(updateAccountRequest.getDistrictId());
        if (district == null)
            throw new ErrorCommon(ErrorCode.DISTRICT_INVALID, Translator.toLocale(ErrorCode.DISTRICT_INVALID));

        if (baseService.getAccountService().isPhoneNumberExistedForEDL(baseService.getUtil().toMsisdn(updateAccountRequest.getPhoneNumber()), accountId))
            throw new ErrorCommon(ErrorCode.PHONE_NUMBER_EXISTED, Translator.toLocale(ErrorCode.PHONE_NUMBER_EXISTED));

        UserRepresentation keycloakUser = keycloakUtil.findByUsername(account.getUsername());
        if (keycloakUser == null)
            throw new ErrorCommon(ErrorCode.KEYCLOAK_USER_NOT_FOUND, Translator.toLocale(ErrorCode.KEYCLOAK_USER_NOT_FOUND));

        boolean isUserUpdated = keycloakUtil.updateUser(keycloakUser.getId(), updateAccountRequest.getPassword(), updateAccountRequest.getPhoneNumber(), district.getName(), null);
        if (!isUserUpdated)
            throw new ErrorCommon(ErrorCode.KEYCLOAK_UPDATE_FAILED, Translator.toLocale(ErrorCode.KEYCLOAK_UPDATE_FAILED));

        List<RoleAccount> roleAccountList = baseService.getRoleService().findByIdAccountId(accountId);
        Set<String> currentRoleSet = roleAccountList.parallelStream().map(roleAccount -> roleAccount.getId().getRoleCode()).collect(Collectors.toSet());
        Set<String> updateRoleSet = new HashSet<>(updateAccountRequest.getRoleList());

        boolean hasNewRole = updateRoleSet.parallelStream().anyMatch(role -> !currentRoleSet.contains(role));
        if (!hasNewRole && (currentRoleSet.size() == updateRoleSet.size())) {
            log.info("Role not change");
        } else {
            boolean isRoleUpdated = keycloakUtil.updateRoleUser(keycloakUser.getId(), updateAccountRequest.getRoleList());
            if (!isRoleUpdated)
                throw new ErrorCommon(ErrorCode.KEYCLOAK_UPDATE_FAILED, Translator.toLocale(ErrorCode.KEYCLOAK_UPDATE_FAILED));

            baseService.getRoleService().deleteRoleAccountList(roleAccountList);
            for (String roleCode : updateAccountRequest.getRoleList()) {
                RoleAccount roleAccount = new RoleAccount();
                roleAccount.setId(new RoleAccountId(roleCode, account.getId()));
                roleAccount.setCreatedBy(principal.getName());
                roleAccount = baseService.getRoleService().saveRoleAccount(roleAccount);
            }
        }

        account.setPhoneNumber(baseService.getUtil().toMsisdn(updateAccountRequest.getPhoneNumber()));
        account.setDistrictId(updateAccountRequest.getDistrictId());
        account.setGender(Gender.findByName(updateAccountRequest.getGender()));
        if (updateAccountRequest.getDepartment() != null) account.setDepartment(updateAccountRequest.getDepartment());
        if (updateAccountRequest.getPosition() != null) account.setPosition(updateAccountRequest.getPosition());
        if (updateAccountRequest.getAddress() != null) account.setAddress(updateAccountRequest.getAddress());
        if (updateAccountRequest.getRemark() != null) account.setRemark(updateAccountRequest.getRemark());
        if (updateAccountRequest.getDateOfBirth() != null)
            account.setDateOfBirth(LocalDate.parse(updateAccountRequest.getDateOfBirth(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay());
        account.setUpdatedBy(principal.getName());
        account = baseService.getAccountService().save(account);

        return generateSuccessResponse(updateAccountRequest.getRequestId(), AccountDetail.generate(baseService.getAccountService().findAccountDetail(accountId, AccountDetailView.class)));
    }

    @Override
    public CommonResponse onUploadAvatar(String accountId, MultipartFile file, Principal principal) {
        Account account = baseService.getAccountService().findById(accountId);
        if (account == null)
            throw new ErrorCommon(ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        String fileId = null;

        try {
            fileId = baseService.getStorageService().uploadFile(Constants.ELECTRIC, file, CannedAccessControlList.PublicRead);
        } catch (IOException e) {
            log.error("Upload file error due to ", e);
        }

        if (fileId == null)
            throw new ErrorCommon(ErrorCode.FILE_UPLOAD_ERROR, Translator.toLocale(ErrorCode.FILE_UPLOAD_ERROR));

        if (account.getAvatarId() != null) {
            log.info("Delete old avatar");
            String[] split = account.getAvatarId().split("/");
            baseService.getStorageService().deleteFile(Constants.ELECTRIC, split[split.length - 1]);
        }

        account.setAvatarId("https://s3.mytel.com.mm/electric/" + fileId);
        account.setUpdatedBy(principal.getName());
        account = baseService.getAccountService().save(account);
        return generateSuccessResponse(UUID.randomUUID().toString(), AccountDetail.generate(baseService.getAccountService().findAccountDetail(accountId, AccountDetailView.class)));
    }


    @Override
    public CommonResponse onChangeAccountStatus(String accountId) {
        Account account = baseService.getAccountService().findById(accountId);
        if (account == null)
            throw new ErrorCommon(ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        account.setIsActive(!account.getIsActive());
        account = baseService.getAccountService().save(account);
        return generateSuccessResponse(UUID.randomUUID().toString(), AccountDetail.generate(baseService.getAccountService().findAccountDetail(accountId, AccountDetailView.class)));
    }
}
