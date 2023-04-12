package la.com.unitel.service.imp;

import la.com.unitel.repository.ProvinceRepo;
import la.com.unitel.repository.RoleRepo;
import la.com.unitel.service.ProvinceService;
import la.com.unitel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepo roleRepo;
}
