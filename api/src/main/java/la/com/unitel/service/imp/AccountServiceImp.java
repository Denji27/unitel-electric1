package la.com.unitel.service.imp;

import la.com.unitel.repository.AccountRepo;
import la.com.unitel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class AccountServiceImp implements AccountService {
    @Autowired
    private AccountRepo accountRepo;
}
