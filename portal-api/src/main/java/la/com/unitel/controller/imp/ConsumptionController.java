package la.com.unitel.controller.imp;

import la.com.unitel.business.consumption.IConsumptionCommon;
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
    private IConsumptionCommon iConsumptionCommon;

    @Override
    public ResponseEntity<?> readConsumption(String contractId, ReadConsumptionRequest readConsumptionRequest, Principal principal) {
        return ResponseEntity.ok(iConsumptionCommon.onReadConsumption(contractId, readConsumptionRequest, principal));
    }

    @Override
    public ResponseEntity<?> updateConsumption(String consumptionId, UpdateConsumptionRequest updateConsumptionRequest, Principal principal) {
        return ResponseEntity.ok(iConsumptionCommon.onUpdateConsumption(consumptionId, updateConsumptionRequest, principal));
    }

    @Override
    public ResponseEntity<?> getDetail(String consumptionId) {
        return ResponseEntity.ok(iConsumptionCommon.onConsumptionDetail(consumptionId));
    }

    @Override
    public ResponseEntity<?> getUnreadByReader(String reader, int page, int size) {
        return ResponseEntity.ok(iConsumptionCommon.onGetUnReadByReader(reader, page, size));
    }

    @Override
    public ResponseEntity<?> getHistoryByReader(String reader, LocalDate fromDate, LocalDate toDate, int page, int size) {
        return ResponseEntity.ok(iConsumptionCommon.onGetReadHistoryByReader(reader, fromDate, toDate, page, size));
    }

    @Override
    public ResponseEntity<?> getConsumptionByContractId(String contractId, LocalDate fromDate, LocalDate toDate, int page, int size) {
        return ResponseEntity.ok(iConsumptionCommon.onGetConsumptionHistoryByContractId(contractId, fromDate, toDate, page, size));
    }
}
