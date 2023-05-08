package la.com.unitel.business.account.inquiry;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.business.account.IAccountCommon;
import la.com.unitel.business.account.dto.AccountDetail;
import la.com.unitel.business.account.view.AccountDetailView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
