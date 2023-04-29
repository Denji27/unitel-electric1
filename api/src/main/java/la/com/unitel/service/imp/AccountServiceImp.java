package la.com.unitel.service.imp;

import la.com.unitel.entity.account.Account;
import la.com.unitel.repository.AccountRepo;
import la.com.unitel.service.AccountService;
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
public class AccountServiceImp implements AccountService {
    @Autowired
    private AccountRepo accountRepo;

    @Override
    public boolean existsByUsername(String username) {
        return accountRepo.existsByUsername(username);
    }

    @Override
    public Account save(Account account) {
        return accountRepo.save(account);
    }

    @Override
    public Account findById(String id) {
        return accountRepo.findById(id).orElse(null);
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepo.findByUsername(username).orElse(null);
    }

    @Override
    public <T> Page<T> searchAccountDetail(String input, Class<T> type, Pageable pageable) {
        return accountRepo.searchAccountDetail('%' + input + '%', type, pageable);
    }

    @Override
    public <T> T findAccountDetail(String accountId, Class<T> type) {
        return accountRepo.findAccountDetail(accountId, type);
    }

    @Override
    public <T> Page<T> findAccountByRole(String role, Class<T> type, Pageable pageable) {
        return accountRepo.findAccountByRole(role, type, pageable);
    }
}
