package la.com.unitel.business.account.inquiry;

import la.com.unitel.*;
import la.com.unitel.business.account.IAccountCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
