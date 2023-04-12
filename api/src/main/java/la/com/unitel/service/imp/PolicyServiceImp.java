package la.com.unitel.service.imp;

import la.com.unitel.repository.MeterDeviceRepo;
import la.com.unitel.repository.PolicyRepo;
import la.com.unitel.service.MeterDeviceService;
import la.com.unitel.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class PolicyServiceImp implements PolicyService {
    @Autowired
    private PolicyRepo policyRepo;
}
