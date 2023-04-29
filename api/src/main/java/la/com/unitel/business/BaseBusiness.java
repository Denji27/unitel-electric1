package la.com.unitel.business;

import com.mytel.b2b.service.StorageService;
import la.com.unitel.Util;
import la.com.unitel.service.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public class BaseBusiness {
    @Autowired
    public Util util;
    @Autowired
    public AccountService accountService;
    @Autowired
    public RoleService roleService;
    @Autowired
    public DistrictService districtService;
    @Autowired
    public ContractTypeService contractTypeService;
    @Autowired
    public ContractService contractService;
    @Autowired
    public MeterDeviceService deviceService;
    @Autowired
    public ConsumptionService consumptionService;
    @Autowired
    public BillService billService;
    @Autowired
    public TransactionService transactionService;

    @Autowired
    public StorageService storageService;

    public CommonResponse generateSuccessResponse(String requestId, Object result) {
        CommonResponse commonResponse = new CommonResponse("00000", "Success", result);
        commonResponse.setRequestId(requestId);
        return commonResponse;
    }

    public CommonResponse generateFailResponse(String requestId, String errorCode, String message, String referenceId) {
        return null;
    }

}


