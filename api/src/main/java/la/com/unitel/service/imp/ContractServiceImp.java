package la.com.unitel.service.imp;

import la.com.unitel.repository.ConsumptionRepo;
import la.com.unitel.repository.ContractRepo;
import la.com.unitel.service.ConsumptionService;
import la.com.unitel.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class ContractServiceImp implements ContractService {
    @Autowired
    private ContractRepo contractRepo;
}
