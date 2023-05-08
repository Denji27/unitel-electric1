package la.com.unitel.business.account.inquiry;

import la.com.unitel.CommonResponse;
import org.springframework.data.domain.Pageable;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IInquiryAccount {
    CommonResponse onViewAccountDetail(String accountId);
    CommonResponse onSearchAccount(String input, Pageable pageable);
}
