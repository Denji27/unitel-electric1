package la.com.unitel.service.imp;

import la.com.unitel.repository.RoleRepo;
import la.com.unitel.repository.TransactionRepo;
import la.com.unitel.service.RoleService;
import la.com.unitel.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class TransactionServiceImp implements TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;
}
