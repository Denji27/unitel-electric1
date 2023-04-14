package la.com.unitel.business.account.dto;

import la.com.unitel.business.BaseRequest;
import la.com.unitel.entity.constant.Gender;
import la.com.unitel.exception.validation.GenderRegex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequest extends BaseRequest {
    private String password;
    @NotBlank
    private String phoneNumber;
    @NotEmpty
    private List<String> roleList;
    @NotBlank
    private String districtId;
    @GenderRegex
    private String gender;
    private String department;
    private String position;
    private String address;
    private String remark;
}
