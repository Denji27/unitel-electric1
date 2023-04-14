package la.com.unitel.service;

import la.com.unitel.entity.account.Role;
import la.com.unitel.entity.account.RoleAccount;

import java.util.List;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public interface RoleService {

    Role findById(String id);
    boolean existsByCode(String code);
    RoleAccount saveRoleAccount(RoleAccount roleAccount);
    List<RoleAccount> findByIdAccountId(String accountId);
    void deleteRoleAccountList(List<RoleAccount> roleAccountList);
}
