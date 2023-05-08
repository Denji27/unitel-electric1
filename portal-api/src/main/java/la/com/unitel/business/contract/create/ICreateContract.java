package la.com.unitel.business.contract.create;

import la.com.unitel.CommonResponse;
import la.com.unitel.business.contract.create.dto.CreateContractRequest;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface ICreateContract {
    CommonResponse onCreateContract(CreateContractRequest createContractRequest, Principal principal);
}
