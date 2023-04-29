package la.com.unitel.business.account.dto;

import la.com.unitel.business.BaseRequest;
import la.com.unitel.business.account.view.AccountDetailView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountContractLinkRequest extends BaseRequest {
    @NotBlank
    private String accountId;

    @NotEmpty
    private List<String> contractIds;
}