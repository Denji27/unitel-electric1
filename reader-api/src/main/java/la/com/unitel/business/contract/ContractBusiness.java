package la.com.unitel.business.contract;

import la.com.unitel.*;
import la.com.unitel.business.contract.dto.ContractDetail;
import la.com.unitel.business.contract.dto.projection.ContractDetailView;
import la.com.unitel.entity.usage_payment.Contract;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
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
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class ContractBusiness extends BaseBusiness implements IContract {
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
