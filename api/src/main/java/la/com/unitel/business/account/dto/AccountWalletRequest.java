package la.com.unitel.business.account.dto;

import la.com.unitel.business.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountWalletRequest extends BaseRequest {
    @NotBlank
    private String accountId;
    @NotBlank
    private String walletAccount;
    @NotBlank
    private String walletName;
}