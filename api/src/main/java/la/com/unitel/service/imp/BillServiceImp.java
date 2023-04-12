package la.com.unitel.service.imp;

import la.com.unitel.repository.AccountRepo;
import la.com.unitel.repository.BillRepo;
import la.com.unitel.service.AccountService;
import la.com.unitel.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class BillServiceImp implements BillService {
    @Autowired
    private BillRepo billRepo;
}
