package la.com.unitel.controller.imp;

import la.com.unitel.business.consumption.IConsumptionCommon;
import la.com.unitel.business.consumption.inquiry.IInquiryConsumption;
import la.com.unitel.business.consumption.reader_task.IReaderConsumption;
import la.com.unitel.controller.ConsumptionAPIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RestController
public class ConsumptionController implements ConsumptionAPIs {
    @Autowired
    private IInquiryConsumption iInquiryConsumption;

    @Autowired
    private IReaderConsumption iReaderConsumption;

    /*@Override
    public ResponseEntity<?> readConsumption(String contractId, ReadConsumptionRequest readConsumptionRequest, Principal principal) {
        return ResponseEntity.ok(iConsumptionCommon.onReadConsumption(contractId, readConsumptionRequest, principal));
    }

    @Override
    public ResponseEntity<?> updateConsumption(String consumptionId, UpdateConsumptionRequest updateConsumptionRequest, Principal principal) {
        return ResponseEntity.ok(iConsumptionCommon.onUpdateConsumption(consumptionId, updateConsumptionRequest, principal));
    }*/

    @Override
    public ResponseEntity<?> getDetail(String consumptionId) {
        return ResponseEntity.ok(iInquiryConsumption.onConsumptionDetail(consumptionId));
    }

    @Override
    public ResponseEntity<?> getUnreadByReader(String reader, int page, int size) {
        return ResponseEntity.ok(iReaderConsumption.onGetUnReadByReader(reader, page, size));
    }

    @Override
    public ResponseEntity<?> getHistoryByReader(String reader, LocalDate fromDate, LocalDate toDate, int page, int size) {
        return ResponseEntity.ok(iReaderConsumption.onGetReadHistoryByReader(reader, fromDate, toDate, page, size));
    }

    @Override
    public ResponseEntity<?> getConsumptionByContractId(String contractId, LocalDate fromDate, LocalDate toDate, int page, int size) {
        return ResponseEntity.ok(iInquiryConsumption.onGetConsumptionHistoryByContractId(contractId, fromDate, toDate, page, size));
    }
}
