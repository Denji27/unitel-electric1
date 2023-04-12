package la.com.unitel.repository;

import la.com.unitel.entity.account.RoleAccount;
import la.com.unitel.entity.account.RoleAccountId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface RoleAccountRepo extends JpaRepository<RoleAccount, RoleAccountId> {
}
