package la.com.unitel.business.account.inquiry;

import la.com.unitel.CommonResponse;
import la.com.unitel.business.account.create.dto.CreateAccountRequest;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IInquiryAccount {
    CommonResponse onViewAccountDetail(String accountId);
    CommonResponse onSearchAccount(String input, Pageable pageable);
    CommonResponse onGetReaderList(String role, Pageable pageable);
}
