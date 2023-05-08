package la.com.unitel.business.account.update;

import la.com.unitel.CommonResponse;
import la.com.unitel.business.account.update.dto.UpdateAccountRequest;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IUpdateAccount {
    CommonResponse onUpdateAccount(String accountId, UpdateAccountRequest updateAccountRequest, Principal principal);
    CommonResponse onUploadAvatar(String accountId, MultipartFile file, Principal principal);
    CommonResponse onChangeAccountStatus(String accountId);
}
