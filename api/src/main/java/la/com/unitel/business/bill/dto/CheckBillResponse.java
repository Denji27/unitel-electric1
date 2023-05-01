package la.com.unitel.business.bill.dto;

import la.com.unitel.business.bill.view.BillDetailView;
import la.com.unitel.entity.constant.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckBillResponse {
    private Double totalAmount;
    private Double totalDiscount;
    private Double totalFee;
    private List<BillUsageDetail> bills;
}






