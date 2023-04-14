package la.com.unitel.business.account.dto;

import la.com.unitel.business.BaseRequest;
import la.com.unitel.entity.constant.Gender;
import la.com.unitel.exception.validation.GenderRegex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest extends BaseRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String phoneNumber;
    @NotBlank
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
