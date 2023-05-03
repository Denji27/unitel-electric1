package la.com.unitel.business.contract.inquiry;

import la.com.unitel.*;
import la.com.unitel.business.contract.IContractCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class InquiryContractBusiness extends BaseBusiness implements IInquiryContract {
    @Autowired
    private IContractCommon iContractCommon;

    @Override
    public CommonResponse onViewContractDetail(String contractId) {
        return iContractCommon.onViewContractDetail(contractId);
    }

    @Override
    public CommonResponse onSearchContract(String input, Pageable pageable) {
        return iContractCommon.onSearchContract(input, pageable);
    }
}
