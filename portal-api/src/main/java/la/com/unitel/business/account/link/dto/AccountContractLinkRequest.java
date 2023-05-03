package la.com.unitel.business.account.link.dto;

import la.com.unitel.BaseRequest;
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
public class AccountContractLinkRequest extends BaseRequest {
    @NotBlank
    private String accountId;

    @NotEmpty
    private List<String> contractIds;
}