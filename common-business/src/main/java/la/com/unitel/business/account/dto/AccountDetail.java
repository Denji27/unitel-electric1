package la.com.unitel.business.account.dto;

import la.com.unitel.business.account.view.AccountDetailView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDetail {
    private String id;
    private String username;
    private String phoneNumber;
    private String avatarId;
    private String roles;
    private String district;
    private String gender;
    private String department;
    private String position;
    private String address;
    private String remark;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public static AccountDetail generate(AccountDetailView view){
        return AccountDetail.builder()
                .id(view.getId())
                .username(view.getUsername())
                .phoneNumber(view.getPhoneNumber())
                .avatarId(view.getAvatarId())
                .district(view.getDistrict())
                .gender(view.getGender())
                .department(view.getDepartment())
                .position(view.getPosition())
                .address(view.getAddress())
                .remark(view.getRemark())
                .createdBy(view.getCreatedBy())
                .updatedBy(view.getUpdatedBy())
                .isActive(view.getIsActive())
                .createdAt(view.getCreatedAt())
                .roles(view.getRoles())
                .build();
    }
}