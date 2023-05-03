package la.com.unitel.business.account.inquiry;

import la.com.unitel.*;
import la.com.unitel.business.account.IAccountCommon;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author : Tungct
 * @since : 5/3/2023, Wed
 **/
@Service
@Slf4j
public class InquiryAccountBusiness extends BaseBusiness implements IInquiryAccount {
    @Autowired
    private IAccountCommon iAccountCommon;
    @Override
    public CommonResponse onViewAccountDetail(String accountId) {
        return iAccountCommon.onViewAccountDetail(accountId);
    }

    @Override
    public CommonResponse onSearchAccount(String input, Pageable pageable) {
        return iAccountCommon.onSearchAccount(input, pageable);
    }

    @Override
    public CommonResponse onGetReaderList(String role, Pageable pageable) {
        Page<AccountDetailView> views = baseService.getAccountService().findAccountByRole(role, AccountDetailView.class, pageable);
        List<AccountDetail> collect = views.getContent().parallelStream().map(AccountDetail::generate).collect(Collectors.toList());
        Page<AccountDetail> result = new PageImpl<>(collect, pageable, views.getTotalElements());
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }
}
