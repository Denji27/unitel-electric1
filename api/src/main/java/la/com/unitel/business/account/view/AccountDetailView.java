package la.com.unitel.business.account.view;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
public interface AccountDetailView {
    String getId();
    String getUsername();
    String getPhoneNumber();
    String getAvatarId();
    String getDistrict();
    String getGender();
    String getDepartment();
    String getPosition();
    String getAddress();
    String getRemark();
    String getCreatedBy();
    String getUpdatedBy();
    Boolean getIsActive();
    LocalDateTime getCreatedAt();
    String getRoles();
}
