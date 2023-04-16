package la.com.unitel.business.contract.dto;

import la.com.unitel.business.BaseRequest;
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
public class UpdateContractRequest extends BaseRequest {
    private String password;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String districtId;
    @NotBlank
    private String contractType;
    @GenderRegex
    private String gender;
    private String latitude;
    private String longitude;
    private String address;
    private String remark;
}