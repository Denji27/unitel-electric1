package la.com.unitel.controller.imp;

import la.com.unitel.business.consumption.IInquiryConsumption;
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
    private IInquiryConsumption iInquiryConsumption;

    @Override
    public ResponseEntity<?> getConsumptionByContractId(String contractId, LocalDate fromDate, LocalDate toDate, int page, int size) {
        return ResponseEntity.ok(iInquiryConsumption.onGetConsumptionHistoryByContractId(contractId, fromDate, toDate, page, size));
    }

    @Override
    public ResponseEntity<?> getDetail(String consumptionId) {
        return ResponseEntity.ok(iInquiryConsumption.onConsumptionDetail(consumptionId));
    }

    @Override
    public ResponseEntity<?> getStatistic(String contractId, LocalDate fromDate, LocalDate toDate) {
        return ResponseEntity.ok(iInquiryConsumption.onGetStatistic(contractId, fromDate, toDate));
    }
}
