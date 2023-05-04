package la.com.unitel.controller.imp;

import la.com.unitel.business.consumption.inquiry.IInquiryConsumption;
import la.com.unitel.business.consumption.read.IReadConsumption;
import la.com.unitel.business.consumption.read.dto.ReadConsumptionRequest;
import la.com.unitel.business.consumption.reader_history.IHistoryConsumption;
import la.com.unitel.business.consumption.reader_unread.IUnreadConsumption;
import la.com.unitel.business.consumption.update.IUpdateConsumption;
import la.com.unitel.business.consumption.update.dto.UpdateConsumptionRequest;
import la.com.unitel.controller.ConsumptionAPIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RestController
public class ConsumptionController implements ConsumptionAPIs {
    @Autowired
    private IReadConsumption iReadConsumption;

    @Autowired
    private IUpdateConsumption iUpdateConsumption;

    @Autowired
    private IHistoryConsumption iHistoryConsumption;

    @Autowired
    private IUnreadConsumption iUnreadConsumption;

    @Autowired
    private IInquiryConsumption iInquiryConsumption;

    @Override
    public ResponseEntity<?> readConsumption(String contractId, ReadConsumptionRequest readConsumptionRequest, Principal principal) {
        return ResponseEntity.ok(iReadConsumption.onReadConsumption(contractId, readConsumptionRequest, principal));
    }

    @Override
    public ResponseEntity<?> uploadConsumptionImage(MultipartFile file, Principal principal) {
        return ResponseEntity.ok(iReadConsumption.uploadConsumptionImage(file, principal));
    }

    @Override
    public ResponseEntity<?> updateConsumption(String consumptionId, UpdateConsumptionRequest updateConsumptionRequest, Principal principal) {
        return ResponseEntity.ok(iUpdateConsumption.onUpdateConsumption(consumptionId, updateConsumptionRequest, principal));
    }

    @Override
    public ResponseEntity<?> getUnreadByReader(int page, int size, Principal principal) {
        return ResponseEntity.ok(iUnreadConsumption.onGetUnReadByReader(principal.getName(), page, size));
    }

    @Override
    public ResponseEntity<?> getHistoryByReader(LocalDate fromDate, LocalDate toDate, int page, int size, Principal principal) {
        return ResponseEntity.ok(iHistoryConsumption.onGetReadHistoryByReader(principal.getName(), fromDate, toDate, page, size));
    }

    @Override
    public ResponseEntity<?> getDetail(String consumptionId) {
        return ResponseEntity.ok(iInquiryConsumption.onConsumptionDetail(consumptionId));
    }
}
