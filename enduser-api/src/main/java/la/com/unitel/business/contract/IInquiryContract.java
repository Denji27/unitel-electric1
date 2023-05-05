package la.com.unitel.business.contract;

import la.com.unitel.CommonResponse;
import org.springframework.data.domain.Pageable;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IInquiryContract {
    CommonResponse onViewContractDetail(String contractId);
    CommonResponse onSearchContract(String input, Pageable pageable);
}
