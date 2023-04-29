package la.com.unitel.repository;

import la.com.unitel.entity.account.AccountContract;
import la.com.unitel.entity.account.AccountContractId;
import la.com.unitel.entity.account.RoleAccount;
import la.com.unitel.entity.account.RoleAccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface AccountContractRepo extends JpaRepository<AccountContract, AccountContractId> {
}
