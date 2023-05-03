package la.com.unitel;

import com.mytel.b2b.service.StorageService;
import la.com.unitel.service.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author : Tungct
 * @since : 4/21/2023, Fri
 **/
@Getter
@Slf4j
@Service
public class BaseService {
    public final Util util;
    public final AccountService accountService;
    public final RoleService roleService;
    public final DistrictService districtService;
    public final ContractTypeService contractTypeService;
    public final ContractService contractService;
    public final MeterDeviceService deviceService;
    public final ConsumptionService consumptionService;
    public final BillService billService;
    public final TransactionService transactionService;
    public final StorageService storageService;

    public BaseService(Util util, AccountService accountService, RoleService roleService, DistrictService districtService, ContractTypeService contractTypeService, ContractService contractService, MeterDeviceService deviceService, ConsumptionService consumptionService, BillService billService, TransactionService transactionService, StorageService storageService) {
        this.util = util;
        this.accountService = accountService;
        this.roleService = roleService;
        this.districtService = districtService;
        this.contractTypeService = contractTypeService;
        this.contractService = contractService;
        this.deviceService = deviceService;
        this.consumptionService = consumptionService;
        this.billService = billService;
        this.transactionService = transactionService;
        this.storageService = storageService;
    }
}
