package la.com.unitel.business.contract.update;

import la.com.unitel.CommonResponse;
import la.com.unitel.business.contract.update.dto.UpdateContractRequest;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IUpdateContract {
    CommonResponse onUpdateContract(String contractId, UpdateContractRequest updateContractRequest, Principal principal);
    CommonResponse onUploadAvatar(String contractId, MultipartFile file, Principal principal);
}
