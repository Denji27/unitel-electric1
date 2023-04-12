package la.com.unitel.service.imp;

import la.com.unitel.repository.PolicyRepo;
import la.com.unitel.repository.ProvinceRepo;
import la.com.unitel.service.PolicyService;
import la.com.unitel.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class ProvinceServiceImp implements ProvinceService {
    @Autowired
    private ProvinceRepo provinceRepo;
}
