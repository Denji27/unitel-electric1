package la.com.unitel.business.contract.view;

import java.time.LocalDateTime;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
public interface ContractDetailView {
    String getId();
    String getName();
    String getUsername();
    String getPhoneNumber();
    String getDistrict();
    String getProvince();
    String getGender();
    String getContractType();
    String getMeterCode();
    String getLatitude();
    String getLongitude();
    Boolean getIsActive();
    String getAddress();
    String getRemark();
    String getCreatedBy();
    String getUpdatedBy();
    LocalDateTime getCreatedAt();
}
