package la.com.unitel.service.imp;

import la.com.unitel.business.Constant;
import la.com.unitel.entity.account.Account;
import la.com.unitel.entity.account.AccountContract;
import la.com.unitel.entity.account.AccountWallet;
import la.com.unitel.entity.account.AccountWalletId;
import la.com.unitel.repository.AccountContractRepo;
import la.com.unitel.repository.AccountRepo;
import la.com.unitel.repository.AccountWalletRepo;
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

    @Autowired
    private AccountContractRepo accountContractRepo;
    @Autowired
    private AccountWalletRepo accountWalletRepo;

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
    public Boolean isPhoneNumberExistedForEDL(String phoneNumber, String accountId) {
        return accountRepo.findByPhoneNumber(phoneNumber, Constant.ENDUSER, accountId).size() > 0;
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

    @Override
    public List<AccountContract> save(List<AccountContract> accountContracts) {
        return accountContractRepo.saveAll(accountContracts);
    }

    @Override
    public AccountWallet save(AccountWallet accountWallet) {
        return accountWalletRepo.save(accountWallet);
    }

    @Override
    public Boolean isWalletAccountLinked(String accountId, String walletAccount) {
        if (accountId == null) return accountWalletRepo.existsByIdWalletAccount(walletAccount);
        return accountWalletRepo.existsById(new AccountWalletId(accountId, walletAccount));
    }

    @Override
    public void unlinkAccountWallet(AccountWalletId accountWalletId) {
        accountWalletRepo.deleteById(accountWalletId);
    }

    @Override
    public AccountWallet findById(AccountWalletId accountWalletId) {
        return accountWalletRepo.findById(accountWalletId).orElse(null);
    }
}
