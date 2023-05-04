package la.com.unitel.controller.imp;

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

    @Override
    public ResponseEntity<?> readConsumption(String contractId, ReadConsumptionRequest readConsumptionRequest, Principal principal) {
        return ResponseEntity.ok(iReadConsumption.onReadConsumption(contractId, readConsumptionRequest, principal));
    }

    @Override
    public ResponseEntity<?> updateConsumption(String consumptionId, UpdateConsumptionRequest updateConsumptionRequest, Principal principal) {
        return ResponseEntity.ok(iUpdateConsumption.onUpdateConsumption(consumptionId, updateConsumptionRequest, principal));
    }

    @Override
    public ResponseEntity<?> getUnreadByReader(String reader, int page, int size) {
        return ResponseEntity.ok(iUnreadConsumption.onGetUnReadByReader(reader, page, size));
    }

    @Override
    public ResponseEntity<?> getHistoryByReader(String reader, LocalDate fromDate, LocalDate toDate, int page, int size) {
        return ResponseEntity.ok(iHistoryConsumption.onGetReadHistoryByReader(reader, fromDate, toDate, page, size));
    }
}
