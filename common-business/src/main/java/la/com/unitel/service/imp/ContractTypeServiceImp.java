package la.com.unitel.service.imp;

import la.com.unitel.repository.ContractTypeRepo;
import la.com.unitel.service.ContractTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class ContractTypeServiceImp implements ContractTypeService {
    @Autowired
    private ContractTypeRepo contractTypeRepo;

    @Override
    public boolean existsByCode(String code) {
        return contractTypeRepo.existsByCode(code);
    }
}
