package la.com.unitel.business.bill.dto;

import la.com.unitel.business.account.dto.AccountDetail;
import la.com.unitel.business.contract.dto.PIC;
import la.com.unitel.entity.usage_payment.Bill;
import la.com.unitel.entity.usage_payment.Consumption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillUsageDetail {
    Bill bill;
    Consumption consumption;
    PIC pic;
}







