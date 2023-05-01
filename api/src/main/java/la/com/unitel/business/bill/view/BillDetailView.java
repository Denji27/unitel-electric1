package la.com.unitel.business.bill.view;

import la.com.unitel.entity.constant.BillStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
public interface BillDetailView {
    String getId();
    String getPhoneNumber();
    String getAvatarId();
    BillStatus getStatus();
    BigDecimal getTotalAmount();
    String getDistrict();
    String getProvince();
    String getCreatedBy();
    String getUpdatedBy();
    LocalDateTime getCreatedAt();
}