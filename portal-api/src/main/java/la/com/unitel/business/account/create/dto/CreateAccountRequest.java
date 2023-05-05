package la.com.unitel.business.account.create.dto;

import la.com.unitel.BaseRequest;
import la.com.unitel.exception.validation.DateRegex;
import la.com.unitel.exception.validation.GenderRegex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty
    private List<String> roleList;
    @NotBlank
    private String districtId;

    @GenderRegex
    private String gender;

    @DateRegex
    private String dateOfBirth;
    private String department;
    private String position;
    private String address;
    private String remark;
}
