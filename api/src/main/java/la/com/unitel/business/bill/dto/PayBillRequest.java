package la.com.unitel.business.bill.dto;

import la.com.unitel.business.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayBillRequest extends BaseRequest {
    @NotBlank
    private String referenceId;

    @NotBlank
    private String contractId;

    @NotBlank
    private BigDecimal totalAmount;

    @NotEmpty
    private List<String> billIds;

    private String paidBy;
    private String reason;
}