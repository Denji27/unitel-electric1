package la.com.unitel.service.imp;

import la.com.unitel.repository.ContractTypeRepo;
import la.com.unitel.repository.DistrictRepo;
import la.com.unitel.service.ContractTypeService;
import la.com.unitel.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class DistrictServiceImp implements DistrictService {
    @Autowired
    private DistrictRepo districtRepo;
}
