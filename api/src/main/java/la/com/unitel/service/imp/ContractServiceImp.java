package la.com.unitel.service.imp;

import la.com.unitel.entity.usage_payment.Contract;
import la.com.unitel.repository.ConsumptionRepo;
import la.com.unitel.repository.ContractRepo;
import la.com.unitel.service.ConsumptionService;
import la.com.unitel.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class ContractServiceImp implements ContractService {
    @Autowired
    private ContractRepo contractRepo;

    @Override
    public Contract save(Contract contract) {
        return contractRepo.save(contract);
    }

    @Override
    public Contract findById(String contractId) {
        return contractRepo.findById(contractId).orElse(null);
    }

    @Override
    public <T> Page<T> searchContractDetail(String input, Class<T> type, Pageable pageable) {
        return contractRepo.searchContractDetail(input, type, pageable);
    }

    @Override
    public <T> T findContractDetail(String contractId, Class<T> type) {
        return contractRepo.findContractDetail(contractId, type);
    }
}
