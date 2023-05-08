package la.com.unitel.business.account.link.dto;

import la.com.unitel.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountWalletRequest extends BaseRequest {
    @NotBlank
    private String accountId;
    @NotBlank
    private String walletAccount;
    @NotBlank
    private String walletName;
}