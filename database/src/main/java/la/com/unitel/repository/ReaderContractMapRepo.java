package la.com.unitel.repository;

import la.com.unitel.entity.account.ReaderContractId;
import la.com.unitel.entity.account.ReaderContractMap;
import la.com.unitel.entity.account.RoleAccount;
import la.com.unitel.entity.account.RoleAccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface ReaderContractMapRepo extends JpaRepository<ReaderContractMap, ReaderContractId> {
    boolean existsByIdReaderIdAndIdContractIdAndRole(String readerId, String contractId, String role);

    @Query("select a.username from ReaderContractMap r, Account a where r.id.readerId = a.id and r.id.contractId = :contractId and r.role = :role")
    List<String> findByIdContractIdAndRole(String contractId, String role);
}
