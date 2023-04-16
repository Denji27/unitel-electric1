package la.com.unitel.service;

import la.com.unitel.entity.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public interface AccountService {

    boolean existsByUsername(String username);
    Account save(Account account);
    Account findById(String id);
    <T> Page<T> searchAccountDetail(String input, Class<T> type, Pageable pageable);
    <T> T findAccountDetail(String accountId, Class<T> type);
}
