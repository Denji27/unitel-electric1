package la.com.unitel.service.imp;

import la.com.unitel.entity.account.ReaderContractMap;
import la.com.unitel.entity.usage_payment.Contract;
import la.com.unitel.repository.ContractRepo;
import la.com.unitel.repository.ReaderContractMapRepo;
import la.com.unitel.service.ContractService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ContractServiceImp implements ContractService {
    @Autowired
    private ContractRepo contractRepo;
    @Autowired
    private ReaderContractMapRepo readerContractMapRepo;

    @Override
    public Contract save(Contract contract) {
        return contractRepo.save(contract);
    }

    @Override
    public ReaderContractMap save(ReaderContractMap readerContractMap) {
        return readerContractMapRepo.save(readerContractMap);
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
    public <T> Page<T> findByIdInList(List<String> contractIdList, Class<T> type, Pageable pageable) {
        return contractRepo.findContractDetailByIdIn(contractIdList, type, pageable);
    }

    @Override
    public <T> T findContractDetail(String contractId, Class<T> type) {
        return contractRepo.findContractDetail(contractId, type);
    }

    @Override
    public boolean existsByIdReaderIdAndIdContractIdAndRole(String readerId, String contractId, String role) {
        return readerContractMapRepo.existsByIdReaderIdAndIdContractIdAndRole(readerId, contractId, role);
    }

    @Override
    public List<String> findByIdContractIdAndRole(String contractId, String role) {
        return readerContractMapRepo.findByIdContractIdAndRole(contractId, role);
    }

    @Override
    public List<String> findByIdReaderIdAndRole(String readerId, String role) {
        return readerContractMapRepo.findByIdReaderIdAndRole(readerId, role);
    }

    @Override
    public boolean existsByContractName(String contractName) {
        return contractRepo.existsByName(contractName);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return contractRepo.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public String findReaderOrCashierByContract(String contractId, String role) {
        try {
            return contractRepo.findReaderOrCashierByContractId(contractId, role).orElse(null);
        } catch (Exception e) {
            log.error("Find cashier error due to ", e);
            return null;
        }
    }
}
