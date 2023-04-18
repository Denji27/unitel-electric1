package la.com.unitel.business.bill.dto;

import la.com.unitel.business.bill.view.BillDetailView;
import la.com.unitel.entity.constant.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillResponse {
    private String id;
    private String username;
    private String phoneNumber;
    private String avatarId;
    private BillStatus status;
    private BigDecimal totalAmount;
    private String district;
    private String province;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;

    public static BillResponse generate(BillDetailView view){
        return BillResponse.builder()
                .id(view.getId())
                .username(view.getUsername())
                .phoneNumber(view.getPhoneNumber())
                .avatarId(view.getAvatarId())
                .status(view.getStatus())
                .totalAmount(view.getTotalAmount())
                .district(view.getDistrict())
                .province(view.getProvince())
                .createdBy(view.getCreatedBy())
                .updatedBy(view.getUpdatedBy())
                .createdAt(view.getCreatedAt())
                .build();
    }
}






