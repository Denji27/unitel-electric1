package la.com.unitel.controller.imp;

import la.com.unitel.business.contract.IInquiryContract;
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
    private IInquiryContract iInquiryContract;

    @Override
    public ResponseEntity<?> viewContractDetail(String contractId) {
        return ResponseEntity.ok(iInquiryContract.onViewContractDetail(contractId));
    }

    @Override
    public ResponseEntity<?> search(String input, int page, int size) {
        return ResponseEntity.ok(iInquiryContract.onSearchContract(input, PageRequest.of(page, size)));
    }
}
