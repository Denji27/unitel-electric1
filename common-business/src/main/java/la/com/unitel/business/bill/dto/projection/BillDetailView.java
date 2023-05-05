package la.com.unitel.business.bill.dto.projection;

import la.com.unitel.entity.constant.BillStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
public interface BillDetailView {
    String getBillId();
    String getPhoneNumber();
    String getContractId();
    String getContractName();
    BillStatus getStatus();
    BigDecimal getTotalAmount();
    LocalDateTime getBillingDate();
    LocalDateTime getDueDate();
}
