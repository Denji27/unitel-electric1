package la.com.unitel.business.usage.view;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import la.com.unitel.entity.constant.BillStatus;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
public interface BillDetailView {
    String getId();
    String getUsername();
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