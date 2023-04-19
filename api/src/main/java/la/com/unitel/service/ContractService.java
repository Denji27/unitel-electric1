package la.com.unitel.service;

import la.com.unitel.entity.account.ReaderContractMap;
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
    ReaderContractMap save(ReaderContractMap readerContractMap);

    Contract findById(String contractId);
    <T> Page<T> searchContractDetail(String input, Class<T> type, Pageable pageable);
    <T> Page<T> findByIdInList(List<String> contractIdList, Class<T> type, Pageable pageable);
    <T> T findContractDetail(String contractId, Class<T> type);

    boolean existsByIdReaderIdAndIdContractIdAndRole(String readerId, String contractId, String role);
    List<String> findByIdContractIdAndRole(String contractId, String role);
    List<String> findByIdReaderIdAndRole(String readerId, String role);
}
