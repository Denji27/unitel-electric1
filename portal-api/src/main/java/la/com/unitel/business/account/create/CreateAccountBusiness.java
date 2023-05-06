package la.com.unitel.business.account.create;

import la.com.unitel.*;
import la.com.unitel.business.account.create.dto.CreateAccountRequest;
import la.com.unitel.business.account.dto.AccountDetail;
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

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : Tungct
 * @since : 5/3/2023, Wed
 **/
@Service
@Slf4j
public class CreateAccountBusiness extends BaseBusiness implements ICreateAccount{
    @Autowired
    private KeycloakUtil keycloakUtil;

    @Override
    @Transactional
    public CommonResponse onCreateAccount(CreateAccountRequest createAccountRequest, Principal principal) {
        if (baseService.getAccountService().existsByUsername(createAccountRequest.getUsername()))
            throw new ErrorCommon(createAccountRequest.getRequestId(), ErrorCode.USERNAME_EXISTED, Translator.toLocale(ErrorCode.USERNAME_EXISTED));

        String invalidRole = createAccountRequest.getRoleList().parallelStream().filter(role -> !baseService.getRoleService().existsByCode(role)).findFirst().orElse(null);
        if (invalidRole != null || createAccountRequest.getRoleList().contains(Constants.ENDUSER))
            throw new ErrorCommon(createAccountRequest.getRequestId(), ErrorCode.ROLE_INVALID, Translator.toLocale(ErrorCode.ROLE_INVALID));

        District district = baseService.getDistrictService().findById(createAccountRequest.getDistrictId());
        if (district == null)
            throw new ErrorCommon(createAccountRequest.getRequestId(), ErrorCode.DISTRICT_INVALID, Translator.toLocale(ErrorCode.DISTRICT_INVALID));

        if (baseService.getAccountService().isPhoneNumberExistedForEDL(baseService.getUtil().toMsisdn(createAccountRequest.getPhoneNumber()), null))
            throw new ErrorCommon(createAccountRequest.getRequestId(), ErrorCode.PHONE_NUMBER_EXISTED, Translator.toLocale(ErrorCode.PHONE_NUMBER_EXISTED));

        UserRepresentation keycloakUser = keycloakUtil.createUser(createAccountRequest.getUsername(), createAccountRequest.getPassword(), createAccountRequest.getRoleList(),
                baseService.getUtil().toMsisdn(createAccountRequest.getPhoneNumber()), district.getName(), null);
        if (keycloakUser == null)
            throw new ErrorCommon(createAccountRequest.getRequestId(), ErrorCode.KEYCLOAK_CREATE_FAILED, Translator.toLocale(ErrorCode.KEYCLOAK_CREATE_FAILED));

        Account account = new Account();
        account.setId(keycloakUser.getId());
        account.setUsername(createAccountRequest.getUsername());
        account.setPhoneNumber(baseService.getUtil().toMsisdn(createAccountRequest.getPhoneNumber()));
        account.setDistrictId(createAccountRequest.getDistrictId());
        account.setGender(Gender.findByName(createAccountRequest.getGender()));
        account.setAvatarId(account.getGender().equals(Gender.MALE) ? Constants.AVATAR_MALE_DEFAULT : Constants.AVATAR_FEMALE_DEFAULT);
        account.setDepartment(createAccountRequest.getDepartment());
        account.setPosition(createAccountRequest.getPosition());
        account.setAddress(createAccountRequest.getAddress());
        account.setRemark(createAccountRequest.getRemark());
        account.setCreatedBy(principal.getName());
        if (createAccountRequest.getDateOfBirth() != null)
            account.setDateOfBirth(LocalDate.parse(createAccountRequest.getDateOfBirth(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay());
        account.setIsActive(true);
        account = baseService.getAccountService().save(account);

        for (String roleCode : createAccountRequest.getRoleList()) {
            RoleAccount roleAccount = new RoleAccount();
            roleAccount.setId(new RoleAccountId(roleCode, account.getId()));
            roleAccount.setCreatedBy(principal.getName());
            roleAccount = baseService.getRoleService().saveRoleAccount(roleAccount);
        }

        return generateSuccessResponse(createAccountRequest.getRequestId(), AccountDetail.generate(baseService.getAccountService().findAccountDetail(account.getId(), AccountDetailView.class)));
    }
}
