package la.com.unitel.business.account;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.Translator;
import la.com.unitel.business.account.dto.AccountDetail;
import la.com.unitel.business.account.view.AccountDetailView;
import la.com.unitel.entity.account.Account;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
@Service
@Slf4j
public class AccountBusinessCommon extends BaseBusiness implements IAccountCommon {

    @Override
    public CommonResponse onViewAccountDetail(String accountId) {
        Account account = baseService.getAccountService().findById(accountId);
        if (account == null)
            throw new ErrorCommon(UUID.randomUUID().toString(), ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        AccountDetail result = AccountDetail.generate(baseService.getAccountService().findAccountDetail(accountId, AccountDetailView.class));
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }

    @Override
    public CommonResponse onSearchAccount(String input, Pageable pageable) {
        Page<AccountDetailView> views = baseService.getAccountService().searchAccountDetail(input, AccountDetailView.class, pageable);
        List<AccountDetail> collect = views.getContent().parallelStream().map(AccountDetail::generate).collect(Collectors.toList());
        Page<AccountDetail> result = new PageImpl<>(collect, pageable, views.getTotalElements());
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }
}
