package la.com.unitel.controller.imp;

import la.com.unitel.business.contract.IContractCommon;
import la.com.unitel.controller.ContractAPIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RestController
public class ContractController implements ContractAPIs {
    @Autowired
    private IContractCommon iContractCommon;

    @Override
    public ResponseEntity<?> createContract(CreateContractRequest createContractRequest, Principal principal) {
        return ResponseEntity.ok(iContractCommon.onCreateContract(createContractRequest, principal));
    }

    @Override
    public ResponseEntity<?> updateContract(String contractId, UpdateContractRequest updateContractRequest, Principal principal) {
        return ResponseEntity.ok(iContractCommon.onUpdateContract(contractId, updateContractRequest, principal));
    }

    @Override
    public ResponseEntity<?> uploadAvatar(String contractId, MultipartFile file, Principal principal) {
        return ResponseEntity.ok(iContractCommon.onUploadAvatar(contractId, file, principal));
    }

    @Override
    public ResponseEntity<?> viewContractDetail(String contractId) {
        return ResponseEntity.ok(iContractCommon.onViewContractDetail(contractId));
    }

    @Override
    public ResponseEntity<?> search(String input, int page, int size) {
        return ResponseEntity.ok(iContractCommon.onSearchContract(input, PageRequest.of(page, size)));
    }
}
