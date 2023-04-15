package la.com.unitel.service.imp;

import la.com.unitel.entity.account.Account;
import la.com.unitel.repository.AccountRepo;
import la.com.unitel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public <T> List<T> findAccountDetail(String input, Class<T> type) {
        return accountRepo.findAccountDetail(input, type);
    }
}
