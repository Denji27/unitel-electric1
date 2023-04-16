package la.com.unitel.service;

import la.com.unitel.entity.account.Account;
import la.com.unitel.entity.usage_payment.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public interface ContractService {
    Contract save(Contract contract);
    Contract findById(String contractId);
    <T> Page<T> searchContractDetail(String input, Class<T> type, Pageable pageable);
    <T> T findContractDetail(String contractId, Class<T> type);
}
