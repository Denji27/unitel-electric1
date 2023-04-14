package la.com.unitel.service.imp;

import la.com.unitel.entity.account.Role;
import la.com.unitel.entity.account.RoleAccount;
import la.com.unitel.repository.ProvinceRepo;
import la.com.unitel.repository.RoleAccountRepo;
import la.com.unitel.repository.RoleRepo;
import la.com.unitel.service.ProvinceService;
import la.com.unitel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private RoleAccountRepo roleAccountRepo;

    @Override
    public Role findById(String id) {
        return roleRepo.findById(id).orElse(null);
    }

    @Override
    public boolean existsByCode(String code) {
        return roleRepo.existsByCode(code);
    }

    @Override
    public RoleAccount saveRoleAccount(RoleAccount roleAccount) {
        return roleAccountRepo.save(roleAccount);
    }

    @Override
    public List<RoleAccount> findByIdAccountId(String accountId) {
        return roleAccountRepo.findByIdAccountId(accountId);
    }

    @Override
    public void deleteRoleAccountList(List<RoleAccount> roleAccountList) {
        roleAccountRepo.deleteAll(roleAccountList);
    }
}
