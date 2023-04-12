package la.com.unitel.service.imp;

import la.com.unitel.repository.BillRepo;
import la.com.unitel.repository.ConsumptionRepo;
import la.com.unitel.service.BillService;
import la.com.unitel.service.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class ConsumptionServiceImp implements ConsumptionService {
    @Autowired
    private ConsumptionRepo consumptionRepo;
}
