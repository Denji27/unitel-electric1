package la.com.unitel.business.account;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import la.com.unitel.KeycloakUtil;
import la.com.unitel.business.*;
import la.com.unitel.business.account.dto.AccountDetail;
import la.com.unitel.business.account.dto.CreateAccountRequest;
import la.com.unitel.business.account.dto.UpdateAccountRequest;
import la.com.unitel.business.account.view.AccountDetailView;
import la.com.unitel.business.contract.dto.ContractDetail;
import la.com.unitel.business.contract.view.ContractDetailView;
import la.com.unitel.entity.account.*;
import la.com.unitel.entity.constant.Gender;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
@Service
@Slf4j
public class AccountBusiness extends BaseBusiness implements IAccount{
    @Autowired
    private KeycloakUtil keycloakUtil;

    @Override
    @Transactional
    public CommonResponse onCreateAccount(CreateAccountRequest createAccountRequest, Principal principal) {
        if (accountService.existsByUsername(createAccountRequest.getUsername()))
            throw new ErrorCommon(ErrorCode.USERNAME_EXISTED, Translator.toLocale(ErrorCode.USERNAME_EXISTED));

        String invalidRole = createAccountRequest.getRoleList().parallelStream().filter(role -> !roleService.existsByCode(role)).findFirst().orElse(null);
        if (invalidRole != null || createAccountRequest.getRoleList().contains(Constant.ENDUSER))
            throw new ErrorCommon(ErrorCode.ROLE_INVALID, Translator.toLocale(ErrorCode.ROLE_INVALID));

        District district = districtService.findById(createAccountRequest.getDistrictId());
        if (district == null)
            throw new ErrorCommon(ErrorCode.DISTRICT_INVALID, Translator.toLocale(ErrorCode.DISTRICT_INVALID));

        UserRepresentation keycloakUser = keycloakUtil.createUser(createAccountRequest.getUsername(), createAccountRequest.getPassword(), createAccountRequest.getRoleList(),
                util.toMsisdn(createAccountRequest.getPhoneNumber()), district.getName(), null);
        if (keycloakUser == null)
            throw new ErrorCommon(ErrorCode.KEYCLOAK_CREATE_FAILED, Translator.toLocale(ErrorCode.KEYCLOAK_CREATE_FAILED));

        Account account = new Account();
        account.setId(keycloakUser.getId());
        account.setUsername(createAccountRequest.getUsername());
        account.setPhoneNumber(util.toMsisdn(createAccountRequest.getPhoneNumber()));
        account.setDistrictId(createAccountRequest.getDistrictId());
        account.setGender(Gender.findByName(createAccountRequest.getGender()));
        account.setAvatarId(account.getGender().equals(Gender.MALE) ? Constant.AVATAR_MALE_DEFAULT : Constant.AVATAR_FEMALE_DEFAULT);
        account.setDepartment(createAccountRequest.getDepartment());
        account.setPosition(createAccountRequest.getPosition());
        account.setAddress(createAccountRequest.getAddress());
        account.setRemark(createAccountRequest.getRemark());
        account.setCreatedBy(principal.getName());
        account.setIsActive(true);
        account = accountService.save(account);

        for (String roleCode : createAccountRequest.getRoleList()) {
            RoleAccount roleAccount = new RoleAccount();
            roleAccount.setId(new RoleAccountId(roleCode, account.getId()));
            roleAccount.setCreatedBy(principal.getName());
            roleAccount = roleService.saveRoleAccount(roleAccount);
        }

        return generateSuccessResponse(createAccountRequest.getRequestId(), AccountDetail.generate(accountService.findAccountDetail(account.getId(), AccountDetailView.class)));
    }

    @Override
    @Transactional
    public CommonResponse onUpdateAccount(String accountId, UpdateAccountRequest updateAccountRequest, Principal principal) {
        Account account = accountService.findById(accountId);
        if (account == null)
            throw new ErrorCommon(ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        String invalidRole = updateAccountRequest.getRoleList().parallelStream().filter(role -> !roleService.existsByCode(role)).findFirst().orElse(null);
        if (invalidRole != null)
            throw new ErrorCommon(ErrorCode.ROLE_INVALID, Translator.toLocale(ErrorCode.ROLE_INVALID));

        District district = districtService.findById(updateAccountRequest.getDistrictId());
        if (district == null)
            throw new ErrorCommon(ErrorCode.DISTRICT_INVALID, Translator.toLocale(ErrorCode.DISTRICT_INVALID));

        UserRepresentation keycloakUser = keycloakUtil.findByUsername(account.getUsername());
        if (keycloakUser == null)
            throw new ErrorCommon(ErrorCode.KEYCLOAK_USER_NOT_FOUND, Translator.toLocale(ErrorCode.KEYCLOAK_USER_NOT_FOUND));

        boolean isUserUpdated = keycloakUtil.updateUser(keycloakUser.getId(), updateAccountRequest.getPassword(), updateAccountRequest.getPhoneNumber(), district.getName(), null);
        if (!isUserUpdated)
            throw new ErrorCommon(ErrorCode.KEYCLOAK_UPDATE_FAILED, Translator.toLocale(ErrorCode.KEYCLOAK_UPDATE_FAILED));

        List<RoleAccount> roleAccountList = roleService.findByIdAccountId(accountId);
        Set<String> currentRoleSet = roleAccountList.parallelStream().map(roleAccount -> roleAccount.getId().getRoleCode()).collect(Collectors.toSet());
        Set<String> updateRoleSet = new HashSet<>(updateAccountRequest.getRoleList());

        boolean hasNewRole = updateRoleSet.parallelStream().anyMatch(role -> !currentRoleSet.contains(role));
        if (!hasNewRole && (currentRoleSet.size() == updateRoleSet.size())) {
            log.info("Role not change");
        } else {
            boolean isRoleUpdated = keycloakUtil.updateRoleUser(keycloakUser.getId(), updateAccountRequest.getRoleList());
            if (!isRoleUpdated)
                throw new ErrorCommon(ErrorCode.KEYCLOAK_UPDATE_FAILED, Translator.toLocale(ErrorCode.KEYCLOAK_UPDATE_FAILED));

            roleService.deleteRoleAccountList(roleAccountList);
            for (String roleCode : updateAccountRequest.getRoleList()) {
                RoleAccount roleAccount = new RoleAccount();
                roleAccount.setId(new RoleAccountId(roleCode, account.getId()));
                roleAccount.setCreatedBy(principal.getName());
                roleAccount = roleService.saveRoleAccount(roleAccount);
            }
        }

        account.setPhoneNumber(util.toMsisdn(updateAccountRequest.getPhoneNumber()));
        account.setDistrictId(updateAccountRequest.getDistrictId());
        account.setGender(Gender.findByName(updateAccountRequest.getGender()));
        if (updateAccountRequest.getDepartment() != null) account.setDepartment(updateAccountRequest.getDepartment());
        if (updateAccountRequest.getPosition() != null) account.setPosition(updateAccountRequest.getPosition());
        if (updateAccountRequest.getAddress() != null) account.setAddress(updateAccountRequest.getAddress());
        if (updateAccountRequest.getRemark() != null) account.setRemark(updateAccountRequest.getRemark());
        account.setUpdatedBy(principal.getName());
        account = accountService.save(account);

        return generateSuccessResponse(updateAccountRequest.getRequestId(), AccountDetail.generate(accountService.findAccountDetail(accountId, AccountDetailView.class)));
    }

    @Override
    public CommonResponse onUploadAvatar(String accountId, MultipartFile file, Principal principal) {
        Account account = accountService.findById(accountId);
        if (account == null)
            throw new ErrorCommon(ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        String fileId = null;

        try {
            fileId = storageService.uploadFile(Constant.ELECTRIC, file, CannedAccessControlList.PublicRead);
        } catch (IOException e) {
            log.error("Upload file error due to ", e);
        }

        if (fileId == null)
            throw new ErrorCommon(ErrorCode.FILE_UPLOAD_ERROR, Translator.toLocale(ErrorCode.FILE_UPLOAD_ERROR));

        if (account.getAvatarId() != null) {
            log.info("Delete old avatar");
            String[] split = account.getAvatarId().split("/");
            storageService.deleteFile(Constant.ELECTRIC, split[split.length - 1]);
        }

        account.setAvatarId("https://s3.mytel.com.mm/electric/" + fileId);
        account.setUpdatedBy(principal.getName());
        account = accountService.save(account);
        return generateSuccessResponse(UUID.randomUUID().toString(), AccountDetail.generate(accountService.findAccountDetail(accountId, AccountDetailView.class)));
    }


    @Override
    public CommonResponse onChangeAccountStatus(String accountId) {
        Account account = accountService.findById(accountId);
        if (account == null)
            throw new ErrorCommon(ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        account.setIsActive(!account.getIsActive());
        account = accountService.save(account);
        return generateSuccessResponse(UUID.randomUUID().toString(), AccountDetail.generate(accountService.findAccountDetail(accountId, AccountDetailView.class)));
    }

    @Override
    public CommonResponse onViewAccountDetail(String accountId) {
        Account account = accountService.findById(accountId);
        if (account == null)
            throw new ErrorCommon(ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        AccountDetail result = AccountDetail.generate(accountService.findAccountDetail(accountId, AccountDetailView.class));
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }

    @Override
    public CommonResponse onSearchAccount(String input, Pageable pageable) {
        Page<AccountDetailView> views = accountService.searchAccountDetail(input, AccountDetailView.class, pageable);
        List<AccountDetail> collect = views.getContent().parallelStream().map(AccountDetail::generate).collect(Collectors.toList());
        Page<AccountDetail> result = new PageImpl<>(collect, pageable, views.getTotalElements());
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }

    @Override
    public CommonResponse onGetReaderList(String role, Pageable pageable) {
        Page<AccountDetailView> views = accountService.findAccountByRole(role, AccountDetailView.class, pageable);
        List<AccountDetail> collect = views.getContent().parallelStream().map(AccountDetail::generate).collect(Collectors.toList());
        Page<AccountDetail> result = new PageImpl<>(collect, pageable, views.getTotalElements());
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }
}
