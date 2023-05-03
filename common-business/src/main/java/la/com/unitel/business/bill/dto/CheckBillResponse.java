package la.com.unitel.business.bill.dto;

import la.com.unitel.business.bill.dto.BillUsageDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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






