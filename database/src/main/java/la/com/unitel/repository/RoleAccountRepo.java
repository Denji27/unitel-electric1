package la.com.unitel.repository;

import la.com.unitel.entity.account.RoleAccount;
import la.com.unitel.entity.account.RoleAccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface RoleAccountRepo extends JpaRepository<RoleAccount, RoleAccountId> {
    List<RoleAccount> findByIdAccountId(String accountId);

    @Query(value = "select IFNULL(GROUP_CONCAT(role_code order by role_code SEPARATOR ', '), '')  AS text from role_account where account_id = :accountId ",
            nativeQuery = true)
    String findRoleList(String accountId);
}
