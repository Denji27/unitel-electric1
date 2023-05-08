package la.com.unitel.business.contract;

import la.com.unitel.*;
import la.com.unitel.business.contract.dto.ContractDetail;
import la.com.unitel.business.contract.dto.projection.ContractDetailView;
import la.com.unitel.entity.usage_payment.Contract;
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
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class ContractBusinessCommon extends BaseBusiness implements IContractCommon {

    @Override
    public CommonResponse onViewContractDetail(String contractId) {
        Contract contract = baseService.getContractService().findById(contractId);
        if (contract == null)
            throw new ErrorCommon(UUID.randomUUID().toString(), ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        //TODO missing fields, refer reader app
        return generateSuccessResponse(UUID.randomUUID().toString(),
                ContractDetail.generate(baseService.getContractService().findContractDetail(contract.getId(), ContractDetailView.class)));
    }

    @Override
    public CommonResponse onSearchContract(String input, Pageable pageable) {
        Page<ContractDetailView> views = baseService.getContractService().searchContractDetail(input, ContractDetailView.class, pageable);
        List<ContractDetail> collect = views.getContent().parallelStream().map(ContractDetail::generate).collect(Collectors.toList());
        Page<ContractDetail> result = new PageImpl<>(collect, pageable, views.getTotalElements());
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }
}
