package la.com.unitel.business.account.dto;

import la.com.unitel.business.BaseRequest;
import la.com.unitel.business.account.view.AccountDetailView;
import la.com.unitel.exception.validation.GenderRegex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDetail {
    private String id;
    private String username;
    private String phoneNumber;
    private List<String> roles;
    private String district;
    private String gender;
    private String department;
    private String position;
    private String address;
    private String remark;
    private String createdBy;
    private String updatedBy;
    private Boolean isAcive;
    private LocalDateTime createdAt;

    public static AccountDetail generate(AccountDetailView view){
        return AccountDetail.builder()
                .id(view.getId())
                .username(view.getUsername())
                .phoneNumber(view.getPhoneNumber())
                .district(view.getDistrict())
                .gender(view.getGender())
                .department(view.getDepartment())
                .position(view.getPosition())
                .address(view.getAddress())
                .remark(view.getRemark())
                .createdBy(view.getCreatedBy())
                .isAcive(view.getIsActive())
                .createdAt(view.getCreatedAt())
                .build();
    }
}