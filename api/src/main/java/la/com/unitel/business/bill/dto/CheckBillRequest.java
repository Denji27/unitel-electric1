package la.com.unitel.business.bill.dto;

import la.com.unitel.business.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckBillRequest extends BaseRequest {
    @NotBlank
    private String contractId;

    @NotEmpty
    private List<String> billIds;
}