package la.com.unitel.business.contract;

import la.com.unitel.business.CommonResponse;
import la.com.unitel.business.contract.dto.CreateContractRequest;
import la.com.unitel.business.contract.dto.UpdateContractRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IContract {
    CommonResponse onCreateContract(CreateContractRequest createContractRequest, Principal principal);
    CommonResponse onUpdateContract(String contractId, UpdateContractRequest updateContractRequest, Principal principal);
    CommonResponse onUploadAvatar(String contractId, MultipartFile file, Principal principal);
    CommonResponse onViewContractDetail(String contractId);
    CommonResponse onSearchContract(String input, Pageable pageable);
}
