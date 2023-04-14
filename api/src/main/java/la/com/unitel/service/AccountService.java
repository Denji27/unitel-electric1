package la.com.unitel.service;

import la.com.unitel.entity.account.Account;

import java.util.List;
import java.util.Optional;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public interface AccountService {

    boolean existsByUsername(String username);
    Account save(Account account);
    Account findById(String id);
}
