package la.com.unitel.business.contract.update.dto;

import la.com.unitel.BaseRequest;
import la.com.unitel.exception.validation.GenderRegex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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