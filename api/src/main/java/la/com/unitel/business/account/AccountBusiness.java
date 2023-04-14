package la.com.unitel.business.account;

import la.com.unitel.business.BaseBusiness;
import la.com.unitel.business.CommonResponse;
import la.com.unitel.business.KeycloakUtil;
import la.com.unitel.business.Translator;
import la.com.unitel.business.account.dto.CreateAccountRequest;
import la.com.unitel.business.account.dto.UpdateAccountRequest;
import la.com.unitel.entity.account.*;
import la.com.unitel.entity.constant.Gender;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import la.com.unitel.exception.validation.GenderRegex;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.awt.print.Pageable;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        if (invalidRole != null)
            throw new ErrorCommon(ErrorCode.ROLE_INVALID, Translator.toLocale(ErrorCode.ROLE_INVALID));

        District district = districtService.findById(createAccountRequest.getDistrictId());
        if (district == null)
            throw new ErrorCommon(ErrorCode.DISTRICT_INVALID, Translator.toLocale(ErrorCode.DISTRICT_INVALID));


        UserRepresentation keycloakUser = keycloakUtil.createUser(createAccountRequest.getUsername(), createAccountRequest.getPassword(), createAccountRequest.getRoleList(),
                util.toMsisdn(createAccountRequest.getPhoneNumber()), district.getName());
        if (keycloakUser == null)
            throw new ErrorCommon(ErrorCode.KEYCLOAK_CREATE_FAILED, Translator.toLocale(ErrorCode.KEYCLOAK_CREATE_FAILED));

        Account account = new Account();
        account.setId(keycloakUser.getId());
        account.setUsername(createAccountRequest.getUsername());
        account.setPhoneNumber(util.toMsisdn(createAccountRequest.getPhoneNumber()));
        account.setDistrictId(createAccountRequest.getDistrictId());
        account.setGender(Gender.findByName(createAccountRequest.getGender()));
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

        return generateSuccessResponse(createAccountRequest.getRequestId(), account);
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

        boolean isUserUpdated = keycloakUtil.updateUser(keycloakUser.getId(), updateAccountRequest.getPassword(), updateAccountRequest.getPhoneNumber(), district.getName());
        if (!isUserUpdated)
            throw new ErrorCommon(ErrorCode.KEYCLOAK_UPDATE_FAILED, Translator.toLocale(ErrorCode.KEYCLOAK_UPDATE_FAILED));

        List<RoleAccount> roleAccountList = roleService.findByIdAccountId(accountId);
        String newUpdateRole = updateAccountRequest.getRoleList().parallelStream()
                .filter(role -> !roleAccountList.parallelStream().map(roleAccount -> roleAccount.getId().getRoleCode()).collect(Collectors.toSet()).contains(role))
                .findFirst().orElse(null);
        if (newUpdateRole == null) {
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
        account.setDepartment(updateAccountRequest.getDepartment());
        account.setPosition(updateAccountRequest.getPosition());
        account.setAddress(updateAccountRequest.getAddress());
        account.setRemark(updateAccountRequest.getRemark());
        account.setUpdatedBy(principal.getName());
        account = accountService.save(account);

        return generateSuccessResponse(updateAccountRequest.getRequestId(), account);
    }

    @Override
    public CommonResponse onChangeAccountStatus(String accountId) {
        return null;
    }

    @Override
    public CommonResponse onViewAccountDetail(String accountId) {
        return null;
    }

    @Override
    public CommonResponse onSearchAccount(String input, Pageable pageable) {
        return null;
    }
}
